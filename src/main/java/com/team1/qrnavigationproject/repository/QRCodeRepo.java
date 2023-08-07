package com.team1.qrnavigationproject.repository;

import com.team1.qrnavigationproject.model.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QRCodeRepo extends JpaRepository<QRCode, Integer> {
    QRCode findQRCodeById(int id);
    QRCode findQRCodeByDescription(String description);
    QRCode findQRCodeByContentId(int id);
    QRCode findQRCodeBySpaceId(int id);
    QRCode findQRCodeBySubSpaceId(int id);
    QRCode findQRCodeByCreatedAt(LocalDateTime dateTime);
    @Query("SELECT q FROM QRCode q")
    List<QRCode> findAllQRCodes();

}
