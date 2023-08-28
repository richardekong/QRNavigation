package com.team1.qrnavigationproject.lightweightContainerTest;

import com.google.zxing.WriterException;
import com.team1.qrnavigationproject.model.QRCode;
import com.team1.qrnavigationproject.repository.QRCodeRepo;
import com.team1.qrnavigationproject.response.CustomException;
import com.team1.qrnavigationproject.service.QRCodeServiceImpl;
import com.team1.qrnavigationproject.service.QRImageServiceImpl;
import com.team1.qrnavigationproject.service.S3ServiceImpl;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QRCodeServiceTest {

    @Mock
    private QRCodeRepo repo;

    @InjectMocks
    private QRCodeServiceImpl qrCodeService;

    @Mock
    private QRImageServiceImpl qrImageService;

    @Mock
    private S3ServiceImpl s3Service;

    private QRCode qr;

    @BeforeEach
    void setUp() {
        qr = TestData.createQRCode();
        var space = TestData.createSpace();
        var subspace = TestData.createSubSpace();
        qr.setSpace(space);
        qr.setSubSpace(subspace);
    }

    @AfterEach
    void tearDown() {
        repo.deleteAll();
    }

    @Test
    @DisplayName("Verify if QRCode can be saved")
    void canBeSaved() throws WriterException, IOException {
        var qrToBeSaved = qr;
        given(repo.save(qrToBeSaved)).willReturn(qrToBeSaved);
        var savedQR = qrCodeService.save(qrToBeSaved);
        then(repo).should().save(qrToBeSaved);
        assertThat(savedQR).isNotNull();
    }

    @Test
    @DisplayName("Verify if QRCode can be updated")
    public void canBeUpdated() throws WriterException, IOException {
        // Mock repository behavior for existsById and findQRCodeById
        QRCode existingQRCode = qr;
        when(repo.existsById(1)).thenReturn(true);
        when(repo.findQRCodeById(1)).thenReturn(existingQRCode);
        // Update QRCode
        QRCode updatedQRCode = new QRCode(
                qr.getId(),
                qr.getDescription(),
                qr.getSpace(),
                qr.getSubSpace(),
                qr.getPageURL(),
                qr.getImageURL(),
                qr.getCreatedAt()
        );
        qrCodeService.update(updatedQRCode);

        // Verify interactions and assertions
        verify(repo, times(1)).existsById(eq(1));
        verify(repo, times(1)).findQRCodeById(eq(1));
        verify(repo, times(1)).save(eq(updatedQRCode));
    }

    @Test
    @DisplayName("Verify if non-existent QRCode can't be updated")
    public void nonExistentQRCantBeUpdated() throws WriterException, IOException {
        // Mock repository behavior to determine if the qrcode exists
        when(repo.existsById(1)).thenReturn(false);
        QRCode qrToUpdate = qr;

        //verify exception is thrown in absence of the qr to update
        assertThrows(CustomException.class, () -> qrCodeService.update(qr));
        // Verify interactions and assertions
        verify(repo, times(1)).existsById(eq(1));
        verify(repo, never()).findQRCodeById(eq(1));
        verify(repo, never()).save(eq(qrToUpdate));
    }

    @Test
    @DisplayName("Verify if qrcode can be downloaded")
    void canBeDownloaded() {
        //Mock repository's behavior to determine the presence of a qr code in the database
        var qrToDownload = qr;
        when(repo.findQRCodeById(1)).thenReturn(qr);
        qrToDownload = repo.findQRCodeById(1);
        s3Service.getS3Object(qrToDownload.getImageURL());
        assertThrows(CustomException.class, () -> qrCodeService.download(100));
        verify(repo, times(1)).findQRCodeById(qr.getId());
        verify(s3Service, times(1)).getS3Object(anyString());
    }

}
