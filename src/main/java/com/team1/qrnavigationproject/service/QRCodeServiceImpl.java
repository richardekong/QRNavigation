package com.team1.qrnavigationproject.service;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
            throw new CustomException("This QRCode exists Already", HttpStatus.CONFLICT);
        }
        //generate an image file from the qrCode data
        String imageName = UUID.randomUUID().toString();
        File qrImageFile = qrImageService.makeQRImage(imageName, qrCode.getPageURL());
        //save the file to amazon s3 bucket and delete it locally, to avoid clustering the server with qrcode images
        String imageURL = s3Service.putObjectInS3(qrImageFile);
        //set the image url of the qrcode to it's s3 location
        qrCode.setImageURL(imageURL);
        //save the qrCode to the database and return the response
        return qrCodeRepo.save(qrCode);
    }

    @Override
    public QRCode update(QRCode qrCode) throws WriterException, IOException {
        //get the qr code to be updated
        if (!qrCodeRepo.existsById(qrCode.getId())) {
            throw new CustomException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
        QRCode qrTobeUpdated = qrCodeRepo.findQRCodeById(qrCode.getId());
        //determine if there is a change in the space or subspace
        boolean spaceOrSubSpaceIsTheSame = (qrCode.getSpaceId() == qrTobeUpdated.getSpaceId())
                || (qrCode.getSubSpaceId() == qrTobeUpdated.getSubSpaceId());
        //if the space or subspace is not the same, replace QRCode image, and encode with a new pageURL
        if (!spaceOrSubSpaceIsTheSame) {
            //generate and overwrite the qrCode in s3 bucket
            String imageURL = qrTobeUpdated.getImageURL();
            String imageFileName = imageURL.substring(imageURL.lastIndexOf("/"));
            File qrImageFile = qrImageService.makeQRImage(imageFileName, qrCode.getPageURL());
            //upload the generated qr code to s3 bucket
            imageURL = s3Service.putObjectInS3(qrImageFile);
            qrCode.setImageURL(imageURL);
        }
        return qrCodeRepo.save(qrCode);
    }

    @Override
    public InputStreamResource download(int id) {
        //get the qrcode information from the database
        QRCode qrToDownload = qrCodeRepo.findQRCodeById(id);
        if (qrToDownload == null){
            throw new CustomException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
        String imageURL = qrToDownload.getImageURL();
        //get the filename from the image URL after the last slash character
        String imageFileName =  imageURL.substring(imageURL.lastIndexOf("/"));
        S3ObjectInputStream s3Stream = s3Service.getS3Object(imageFileName);
        return new InputStreamResource(s3Stream);
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

