package com.team1.qrnavigationproject.service;

import com.google.zxing.WriterException;
import com.team1.qrnavigationproject.model.QRCode;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QRCodeService {

    QRCode save(QRCode qrCode) throws WriterException, IOException;
    QRCode update(QRCode qrCode) throws WriterException, IOException;

    QRCode patchQRCode(QRCode qrCode);

    QRCode linkQRToSPace(QRCode qRcode) throws IOException;

    InputStreamResource download(int id);

    QRCode findQRCodeById(int qrCode);


    QRCode findQRCodeBySpaceId(int id);

    QRCode findQRCodeBySubSpaceId(int id);

    Optional<QRCode> findQRCodeBySpaceIdAndSubspaceId(int spaceId, int subspaceId);


    Optional<List<QRCode>> findQRCodesByOrganizationId(int id);

    QRCode findQRCodeByCreatedAt(LocalDateTime dateTime);

    List<QRCode> findAllQRCodes();

    void deleteById(int id) throws Exception;

}
