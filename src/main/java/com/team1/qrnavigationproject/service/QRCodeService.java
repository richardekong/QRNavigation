package com.team1.qrnavigationproject.service;

import com.google.zxing.WriterException;
import com.team1.qrnavigationproject.model.QRCode;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface QRCodeService {

    QRCode save(QRCode qrCode) throws WriterException, IOException;

    QRCode update(QRCode qrCode) throws WriterException, IOException;

    InputStreamResource download(int id);

    QRCode findQRCodeById(int qrCode);

    QRCode findQRCodeByContentId(int id);

    QRCode findQRCodeBySpaceId(int id);

    QRCode findQRCodeBySubSpaceId(int id);

    QRCode findQRCodeByCreatedAt(LocalDateTime dateTime);

    List<QRCode> findAllQRCodes();

}
