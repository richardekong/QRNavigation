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
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(QRCodeService.class.getSimpleName());

    @Override
    public QRCode save(QRCode qrCode) throws WriterException, IOException {

        if (qrCodeRepo.existsById(qrCode.getId())
                || qrCodeRepo.existsQRCodeBySpaceIdAndSubSpaceId(
                qrCode.getSpace().getId(),
                qrCode.getSubSpace().getId())) {
            throw new CustomException("This QRCode exists Already", HttpStatus.CONFLICT);
        }

        LOGGER.log(Level.INFO, "Saving QRCode for %s".formatted(qrCode.getSubSpace().getName()));
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
        boolean spaceOrSubSpaceIsTheSame = (qrCode.getSpace().getId() == qrTobeUpdated.getSpace().getId())
                || (qrCode.getSubSpace().getId() == qrTobeUpdated.getSubSpace().getId());
        //if the space or subspace is not the same, replace QRCode image, and encode with a new pageURL
        if (!spaceOrSubSpaceIsTheSame) {
            //generate and overwrite the qrCode in s3 bucket
            String imageURL = qrTobeUpdated.getImageURL();
            String imageFileName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
            File qrImageFile = qrImageService.makeQRImage(imageFileName, qrCode.getPageURL());
            //upload the generated qr code to s3 bucket
            imageURL = s3Service.putObjectInS3(qrImageFile);
            qrCode.setImageURL(imageURL);
        }
        return qrCodeRepo.save(qrCode);
    }

    @Override
    public QRCode patchQRCode(QRCode qrCode) {
        return qrCodeRepo.save(qrCode);
    }

    @Override
    public QRCode linkQRToSPace(QRCode qrCodeToLink) throws IOException {
        //Determine the filename fn1 of the QR code q1 to link
        String fn1 = qrCodeToLink.getImageURL().substring(qrCodeToLink.getImageURL().lastIndexOf("/") + 1);
        //Determine the filename fn2 of the QR code q2 associate with the space, s1 and subspace sp1 to be linked
        int spaceId = qrCodeToLink.getSpace().getId();
        int subspaceId = qrCodeToLink.getSubSpace().getId();
        String fn2 = qrCodeRepo.findQRCodeBySpaceIdAndSubspaceId(spaceId, subspaceId)
                .map(QRCode::getImageURL)
                .map(url -> url.substring(url.lastIndexOf("/") + 1))
                .orElse("");

        //Download q2 image as input stream s1 from cloud through filename fn2
        S3ObjectInputStream s1 = s3Service.getS3Object(fn2);
        //Download q1 image as input stream s2 cloud through filename fn1
        S3ObjectInputStream s2 = s3Service.getS3Object(fn1);
        //save s1 as file f1 (q2) with name fn2 in classpath
        File f1 = s3Service.download(fn2, s1);
        //save s2 as file f2 (q1) with name fn1 in classpath
        File f2 = s3Service.download(fn1, s2);
        //overwrite qr code q2 from cloud with file f1
        s3Service.putObjectInS3(f1);
        // delete f1 from classpath
        f1.delete();
        //overwrite qr code q1 from cloud with file f2
        s3Service.putObjectInS3(f2);
        //delete f2 from classpath
        f2.delete();

        return qrCodeRepo.save(qrCodeToLink);
    }

    @Override
    public Optional<QRCode> findQRCodeBySpaceIdAndSubspaceId(int spaceId, int subspaceId) {
        return qrCodeRepo.findQRCodeBySpaceIdAndSubspaceId(spaceId, subspaceId);
    }

    @Override
    public Optional<List<QRCode>> findQRCodesByOrganizationId(int id) {
        return qrCodeRepo.findQRCodesByOrganizationId(id);
    }

    @Override
    public InputStreamResource download(int id) {
        //get the qrcode information from the database
        QRCode qrToDownload = qrCodeRepo.findQRCodeById(id);
        if (qrToDownload == null) {
            throw new CustomException(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        }
        LOGGER.log(Level.INFO, "Retrieving information to download QRCode for %s in %s"
                .formatted(qrToDownload.getSubSpace().getName(), qrToDownload.getSpace().getName()));
        String imageURL = qrToDownload.getImageURL();
        //get the filename from the image URL after the last slash character
        String imageFileName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
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

    @Override
    public void deleteById(int id) throws Exception{
        LOGGER.log(Level.INFO, "Retrieving QRCode %d to delete".formatted(id));
        if (!qrCodeRepo.existsById(id)) {
            HttpStatus notFound = HttpStatus.NOT_FOUND;
            throw new CustomException("Error(%d): Failed to delete QRCode %d".formatted(notFound.value(), id), notFound);
        }
        CompletableFuture.supplyAsync(() -> {
            QRCode qrToDelete = qrCodeRepo.findQRCodeById(id);
            String imageURL = qrToDelete.getImageURL();
            String fileName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
            qrCodeRepo.deleteById(id);
            return fileName;
        }).thenApply(fileName -> {
            if (!qrCodeRepo.existsById(id)) {
                LOGGER.log(Level.INFO, "Begin deleting QRCode %d image from S3".formatted(id));
                s3Service.removeObjectFromS3(fileName);
            }
            return "QRCode Image %s deleted".formatted(fileName);
        }).handle((response, error) -> {
            if (error != null) {
                LOGGER.log(Level.INFO, "Could not delete QRCode %d image from S3".formatted(id));
            }
            return response;
        });
    }
}

