package com.team1.qrnavigationproject.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.team1.qrnavigationproject.response.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class S3ServiceImpl implements S3Service {

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    private AmazonS3 amazonS3Client;

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public void setAmazonS3Client(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    private static final Logger LOGGER = Logger.getLogger(S3Service.class.getSimpleName());

    @Override
    public S3ObjectInputStream getS3Object(String fileName) {
        LOGGER.log(Level.INFO, "Started downloading QRCode image with file name %s from S3 Bucket".formatted(fileName));
        return CompletableFuture
                .supplyAsync(() -> amazonS3Client.getObject(s3BucketName, fileName))
                .thenApply(S3Object::getObjectContent)
                .handle((response, error) -> {
                    if (error != null) {
                        LOGGER.log(Level.SEVERE, "Failed to retrieve input stream while downloading QRCode with file name %s from S3 Bucket. See details below:\n%s"
                                .formatted(fileName, error.getMessage()));
                    }
                    LOGGER.log(Level.INFO, "Downloaded Input stream for QRCode with filename %s".formatted(fileName));
                    return response;
                })
                .join();
    }

    @Override
    public String putObjectInS3(File file) {
        return CompletableFuture.supplyAsync(() -> {
                    String fileName = file.getName();
                    PutObjectRequest request = new PutObjectRequest(s3BucketName, fileName, file);
                    amazonS3Client.putObject(request);
                    try {
                        Files.delete(file.toPath());
                    } catch (IOException e) {
                        throw new CustomException(
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                HttpStatus.INTERNAL_SERVER_ERROR
                        );
                    }
                    return amazonS3Client.getUrl(s3BucketName, fileName).toString();
                }).handle((response, error) -> {
                    if (error != null) {
                        throw new CustomException(error.getMessage(),
                                HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    return response;
                })
                .join();
    }

    public File download(String fileName, S3ObjectInputStream objectInputStream) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                File file = new File(fileName);
                try (OutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = objectInputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                    return file;
                }
            } catch (Exception e) {
                throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }).handle((response, error) -> {
            if (error != null) {
                logger.log(Level.SEVERE, "Failed to download QRCode image file %s".formatted(fileName));
                logger.log(Level.SEVERE, "See details below: \n %s".formatted(error.getMessage()));
            }
            logger.log(Level.INFO, "QRCode image file %s downloaded successfully".formatted(fileName));
            return response;
        }).join();
    }

    @Override
    public void removeObjectFromS3(String fileName) {
        LOGGER.log(Level.INFO, "Deleting QRCode image file name %s from S3 Bucket.".formatted(fileName));
        CompletableFuture.supplyAsync(() -> {
            DeleteObjectRequest request = new DeleteObjectRequest(s3BucketName, fileName);
            try {
                amazonS3Client.deleteObject(request);
                return HttpStatus.NO_CONTENT.getReasonPhrase();
            } catch (AmazonServiceException e) {
                throw new AmazonServiceException(e.getMessage());
            }
        }).handle((response, error) -> {
            if (error != null) {
                LOGGER.log(Level.SEVERE, "Failed to delete QRCode image file name %s from S3 Bucket.".formatted(fileName));
                throw new CustomException(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            LOGGER.log(Level.INFO, "QRCode image file name %s was Deleted from S3 Bucket.".formatted(fileName));
            return response;
        });
    }
}
