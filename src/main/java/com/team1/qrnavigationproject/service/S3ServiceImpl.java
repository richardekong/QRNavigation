package com.team1.qrnavigationproject.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.team1.qrnavigationproject.response.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

@Service
public class S3ServiceImpl implements S3Service {

    @Value("${s3.bucket.name}")
    private String s3BucketName;

    private AmazonS3 amazonS3Client;

    @Autowired
    public void setAmazonS3Client(AmazonS3 amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
    }

    @Override
    public S3ObjectInputStream getS3Object(String fileName) {
        return CompletableFuture
                .supplyAsync(() -> amazonS3Client.getObject(s3BucketName, fileName))
                .thenApply(S3Object::getObjectContent)
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
        }).join();
    }
}