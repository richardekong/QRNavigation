package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.Content;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void getEventId() {
        int eventId = 1;
        content.setEvent(TestData.createEvent());
        assertEquals(content.getEvent().getId(), eventId);
        assertNotEquals(content.getEvent().getId(), 0);
    }

    @Test
    void getSpaceId() {
        int spaceId = 1;
        content.setSpace(TestData.createSpace());
        assertEquals(content.getSpace().getId(), spaceId);
        assertNotEquals(content.getSpace().getId(), -1);
    }

    @Test
    void getSubSpaceId() {
        int subSpaceId = 1;
        content.setSubSpace(TestData.createSubSpace());
        assertEquals(content.getSubSpace().getId(), subSpaceId);
        assertNotEquals(content.getSubSpace().getId(), 5);
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
    void setEventId() {
        int id = 4;
        content.setEvent(TestData.createEvent());
        content.getEvent().setId(id);
        assertEquals(content.getEvent().getId(), id);
        assertNotEquals(content.getEvent().getId(), 6);
    }

    @Test
    void setSpaceId() {
        int id = 5;
        content.setSpace(TestData.createSpace());
        content.getSpace().setId(id);
        assertEquals(content.getSpace().getId(), id);
        assertNotEquals(content.getSpace().getId(), 1);
    }

    @Test
    void setSubSpaceId() {
        int id = 0;
        content.setSubSpace(TestData.createSubSpace());
        content.getSubSpace().setId(id);
        assertEquals(content.getSubSpace().getId(), id);
        assertNotEquals(content.getSubSpace().getId(), 1);
    }

    @Test
    void testEquals() {
        Content anotherContent = new Content(
                content.getId(),
                content.getName(),
                content.getDescription(),
                content.getEvent(),
                content.getSpace(),
                content.getSubSpace()
        );

        assertEquals(content, anotherContent);
        content.setId(5);
        assertNotEquals(content, anotherContent);
        System.out.println(content.toString());
    }

}

