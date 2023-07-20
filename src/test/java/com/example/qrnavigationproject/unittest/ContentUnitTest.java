package com.example.qrnavigationproject.unittest;

import com.example.qrnavigationproject.model.Content;
import com.example.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class ContentUnitTest {

    private Content content;


    @BeforeEach
    void setUp() {
        content = TestData.createContent();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        int id = 1;
        assertEquals(content.getId(), id);
        assertNotEquals(content.getId(), 2);
    }

    @Test
    void getDescription() {
        String desc = "Content page for Open day";
        assertEquals(content.getDescription(), desc);
        assertNotEquals(content.getDescription(), "Content pages for Open day");
    }

    @Test
    void getPageURL() {
        String pageURL = "https:www.content.com";
        assertEquals(content.getPageURL(), pageURL);
        assertNotEquals(content.getPageURL(), "http:www.content.com");
    }

    @Test
    void getEventId() {
        int eventId = 1;
        assertEquals(content.getEventId(), eventId);
        assertNotEquals(content.getEventId(), 0);
    }

    @Test
    void getSpaceId() {
        int spaceId = 1;
        assertEquals(content.getSpaceId(), spaceId );
        assertNotEquals(content.getSpaceId(), -1);
    }

    @Test
    void getSubSpaceId() {
        int subSpaceId = 1;
        assertEquals(content.getSubSpaceId(), subSpaceId);
        assertNotEquals(content.getSubSpaceId(), 5);
    }

    @Test
    void setId() {
        int id = 1;
        content.setId(id);
        assertEquals(content.getId(), id);
        assertNotEquals(content.getId(), 7);
    }

    @Test
    void setDescription() {
        String description = "Content page for Open day";
        content.setDescription(description);
        assertEquals(content.getDescription(), description);
        assertNotEquals(content.getDescription(), "Content pages for Open day");
    }

    @Test
    void setPageURL() {
        String pageURL = "https://www.openday.com";
        content.setPageURL(pageURL);
        assertEquals(content.getPageURL(), pageURL);
        assertNotEquals(content.getPageURL(), "https:www.content.com");
    }

    @Test
    void setEventId() {
        int id = 4;
        content.setEventId(id);
        assertEquals(content.getEventId(), id);
        assertNotEquals(content.getEventId(), 6);
    }

    @Test
    void setSpaceId() {
        int id = 5;
        content.setSpaceId(id);
        assertEquals(content.getSpaceId(), id);
        assertNotEquals(content.getSpaceId(), 1);
    }

    @Test
    void setSubSpaceId() {
        int id = 0;
        content.setSubSpaceId(id);
        assertEquals(content.getSubSpaceId(), id);
        assertNotEquals(content.getSubSpaceId(), 1);
    }

    @Test
    void testEquals() {
        Content anotherContent = new Content(
                content.getId(),
                content.getDescription(),
                content.getPageURL(),
                content.getEventId(),
                content.getSpaceId(),
                content.getSubSpaceId()
        );

        assertEquals(content, anotherContent);
        content.setId(5);
        assertNotEquals(content, anotherContent);
        System.out.println(content.toString());
    }

    @Test
    void testHashCode() {
        int hashcode = 1555340388;
        assertEquals(content.hashCode(), hashcode);
        assertNotEquals(content.hashCode(), 1555360388);
        System.out.println(content.toString());
    }

    @Test
    void testToString() {
        String contentString = "Content(id=1, description=Content page for Open day," +
                " pageURL=https:www.content.com, eventId=1, spaceId=1, subSpaceId=1)";
        assertEquals(content.toString(), contentString);
        content.setId(5);
        assertNotEquals(content.toString(), "Content(id=1, description=Content page " +
                "for Open day, pageURL=https:www.content.io, eventId=1, spaceId=1, subSpaceId=1)");
        assertNotNull(content.toString());
    }

    @Test
    void validateContentURL() {
        String pageURL1 = "https://www.content.com",
                pageURL2 = "https:www.content.com";
        content.setPageURL(pageURL1);
        Pattern pattern = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].\\S*$");
        Matcher matcher1 = pattern.matcher(content.getPageURL());
        content.setPageURL(pageURL2);
        Matcher matcher2 = pattern.matcher(content.getPageURL());
        assertTrue(matcher1.matches());
        assertFalse(matcher2.matches());
    }

}