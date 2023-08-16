package com.team1.qrnavigationproject.service;

import com.google.zxing.WriterException;
import com.team1.qrnavigationproject.model.QRCode;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QRCodeService {

    QRCode save(QRCode qrCode) throws WriterException, IOException;

    QRCode update(QRCode qrCode) throws WriterException, IOException;

    QRCode linkQRToSPace(QRCode qRcode) throws IOException;

    InputStreamResource download(int id);

    QRCode findQRCodeById(int qrCode);

    QRCode findQRCodeByContentId(int id);

    QRCode findQRCodeBySpaceId(int id);

    QRCode findQRCodeBySubSpaceId(int id);

    Optional<QRCode> findQRCodeBySpaceIdAndSubspaceId(int spaceId, int subspaceId);

    QRCode findQRCodeByCreatedAt(LocalDateTime dateTime);

    List<QRCode> findAllQRCodes();

    void deleteById(int id);

}
