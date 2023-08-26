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
        qrCode.setSpace(TestData.createSpace());
        qrCode.setSubSpace(TestData.createSubSpace());
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
    void testSpaceId() {
        int id = 10;
        qrCode.getSpace().setId(id);
        assertEquals(qrCode.getSpace().getId(), id);
        init();
        assertNotEquals(qrCode.getSpace().getId(), id);
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


}