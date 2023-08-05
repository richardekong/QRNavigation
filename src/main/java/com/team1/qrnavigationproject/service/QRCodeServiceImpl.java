package com.team1.qrnavigationproject.service;

import com.google.zxing.WriterException;
import com.team1.qrnavigationproject.model.QRCode;
import com.team1.qrnavigationproject.repository.QRCodeRepo;
import com.team1.qrnavigationproject.response.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private QRCodeRepo qrCodeRepo;

    private QRImageService qrImageService;

    private S3Service s3Service;

    @Autowired
    public void setQRCodeRepo(QRCodeRepo qrCodeRepo) {
        this.qrCodeRepo = qrCodeRepo;
    }

    @Autowired
    public void setQrImageService(QRImageService qrImageService) {
        this.qrImageService = qrImageService;
    }

    @Autowired
    public void setS3Service(S3Service s3Service) {
        this.s3Service = s3Service;
    }


    @Override
    public QRCode save(QRCode qrCode) throws WriterException, IOException {
        if (qrCodeRepo.existsById(qrCode.getId())) {
            throw new CustomException(
                    "This QRCode exists Already",
                    HttpStatus.CONFLICT
            );
        }
        //generate an image file from the qrCode data
        String imageName = UUID.randomUUID().toString();
        File qrImageFile = qrImageService.makeQRImage(imageName, qrCode.getPageURL());
        //save the file to amazon s3 bucket and delete it locally, to avoid clustering the server with qrcode images
        return s3Service.putObjectInS3(qrImageFile).thenApply(imageURL -> {
            //set the image url of the qrcode to it's s3 location
            qrCode.setImageURL(imageURL);
            //save the qrCode to the database and return the response
            return qrCodeRepo.save(qrCode);
        }).handle((response, exception) -> { //handle exceptions
            if (exception != null) {
                throw new CustomException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return response;
        }).join();
    }

    @Override
    public InputStreamResource download(int id) {
        //get the qrcode information from the database
        return CompletableFuture
                .supplyAsync(() -> qrCodeRepo.findQRCodeById(id))
                .thenApply(qrCode -> {
                    String imageURL = qrCode.getImageURL();
                    //get the filename from the image URL after the last slash character
                    return imageURL.substring(imageURL.lastIndexOf("/"));
                }).thenCompose(imageFileName -> s3Service.getS3Object(imageFileName))
                .thenApply(InputStreamResource::new)
                .handle((response, exception) -> { //handle exception
                    if (exception != null) {
                        throw new CustomException(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    return response;
                }).join();
    }

    @Override
    public QRCode findQRCodeById(int id) {
        if (!qrCodeRepo.existsById(id)) {
            throw new CustomException(
                    "No such QRCode",
                    HttpStatus.NOT_FOUND
            );
        }
        return qrCodeRepo.findQRCodeById(id);
    }

    @Override
    public QRCode findQRCodeByContentId(int id) {
        return qrCodeRepo.findQRCodeByContentId(id);
    }

    @Override
    public QRCode findQRCodeBySpaceId(int id) {
        return qrCodeRepo.findQRCodeBySpaceId(id);
    }

    @Override
    public QRCode findQRCodeBySubSpaceId(int id) {
        return qrCodeRepo.findQRCodeBySubSpaceId(id);
    }

    @Override
    public QRCode findQRCodeByCreatedAt(LocalDateTime dateTime) {
        return qrCodeRepo.findQRCodeByCreatedAt(dateTime);
    }

    @Override
    public List<QRCode> findAllQRCodes() {
        return qrCodeRepo.findAllQRCodes();
    }
}

