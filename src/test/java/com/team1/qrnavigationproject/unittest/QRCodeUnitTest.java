package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.QRCode;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeUnitTest {

    private QRCode qrCode;

    private void init() {
        qrCode = TestData.createQRCode();
    }

    @BeforeEach
    void setUp() {
        init();
    }

    @AfterEach
    void tearDown() {
        init();
    }

    @Test
    void testId() {
        int id = 100;
        qrCode.setId(id);
        assertEquals(qrCode.getId(), id);
        init();
        assertNotEquals(qrCode.getId(), id);
    }

    @Test
    void testDescription() {
        String desc = "QRCode for Abacws / 3.65";
        qrCode.setDescription(desc);
        assertEquals(qrCode.getDescription(), desc);
        init();
        assertNotEquals(qrCode.getDescription(), desc);
    }

    @Test
    void testContentId() {
        int id = 3;
        qrCode.setContentId(id);
        assertEquals(qrCode.getContentId(), id);
        init();
        assertNotEquals(qrCode.getContentId(), id);
    }

    @Test
    void testSpaceId() {
        int id = 10;
        qrCode.setSpaceId(id);
        assertEquals(qrCode.getSpaceId(), id);
        init();
        assertNotEquals(qrCode.getSpaceId(), id);
    }

    @Test
    void testImageURL() {

    }

    @Test
    void setPageURL() {
        String pageURL = "https://www.openday.com";
        qrCode.setPageURL(pageURL);
        assertEquals(qrCode.getPageURL(), pageURL);
        assertNotEquals(qrCode.getPageURL(), "https:www.content.com");
    }

    @Test
    void validateContentURL() {
        String pageURL1 = "https://www.content.com",
                pageURL2 = "https:www.content.com";
        qrCode.setPageURL(pageURL1);
        Pattern pattern = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].\\S*$");
        Matcher matcher1 = pattern.matcher(qrCode.getPageURL());
        qrCode.setPageURL(pageURL2);
        Matcher matcher2 = pattern.matcher(qrCode.getPageURL());
        assertTrue(matcher1.matches());
        assertFalse(matcher2.matches());
    }

    @Test
    void testCreatedAt() {
        LocalDateTime createdAt = LocalDateTime.of(2023, 3, 4, 4, 0, 0);
        qrCode.setCreatedAt(createdAt);
        assertEquals(qrCode.getCreatedAt(), createdAt);
        init();
        assertNotEquals(qrCode.getCreatedAt(), createdAt);
    }

    @Test
    void testEquals() {
        QRCode similarQRCode = new QRCode(
                qrCode.getId(),
                qrCode.getDescription(),
                qrCode.getContentId(),
                qrCode.getSpaceId(),
                qrCode.getSubSpaceId(),
                qrCode.getPageURL(),
                qrCode.getImageURL(),
                qrCode.getCreatedAt()
        );
        assertEquals(qrCode, similarQRCode);
        assertEquals(qrCode.hashCode(), similarQRCode.hashCode());
        qrCode.setId(0);
        assertNotEquals(qrCode, similarQRCode);
        assertNotEquals(qrCode.hashCode(), similarQRCode.hashCode());
    }

    @Test
    void testToString() {

        String qrcodeString = "QRCode(id=1, " +
                "description=QRCode for Abacws / 3.45, contentId=1, " +
                "spaceId=1, subSpaceId=1, pageURL=https://www.cardiffuni.com, " +
                "imageURL=https://www.cardiffuni.com/qr.png," +
                " createdAt=2023-07-15T14:05)";

        QRCode anotherQRCode = new QRCode();
        assertEquals(qrCode.toString(), qrcodeString);
        assertNotNull(qrCode.toString());
        assertNotEquals(qrCode.toString(), anotherQRCode.toString());
    }
}