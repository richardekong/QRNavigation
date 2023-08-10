package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class EventUnitTest {

    private Event event;

    private void init() {
        event = TestData.createEvent();
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
    void getId() {
        assertEquals(event.getId(), 1);
        assertNotEquals(event.getId(), 0);
    }

    @Test
    void getName() {
        assertEquals(event.getName(), "Open day");
        assertNotEquals(event.getName(), "School Open Day");
    }

    @Test
    void getDescription() {
        assertEquals(event.getDescription(), "An event for open day");
        assertNotEquals(event.getDescription(), "An open day event");
    }

    @Test
    void getOrganizer() {
        Organization organization = TestData.createOrganization();
        Organization anotherOrganization = new Organization();
        organization.add(event);
        assertEquals(event.getOrganizer(), organization);
        assertNotEquals(event.getOrganizer(), anotherOrganization);
    }

    @Test
    void getStart() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 7, 18, 0, 0);
        event.setStart(dateTime);
        assertEquals(event.getStart(), dateTime);
        assertNotEquals(event.getStart(), dateTime.minusHours(5));
    }

    @Test
    void getEnd() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 7, 18, 0, 0);
        event.setEnd(dateTime);
        assertEquals(event.getEnd(), dateTime);
        assertNotEquals(event.getEnd(), LocalDateTime.of(2023, 7, 20, 0, 0));
    }

    @Test
    void setId() {
        event.setId(2);
        assertEquals(event.getId(), 2);
        assertNotEquals(event.getId(), 1);
    }

    @Test
    void setName() {
        event.setName("Graduation Ceremony");
        assertEquals(event.getName(), "Graduation Ceremony");
        assertNotEquals(event.getName(), "Open Day");
    }

    @Test
    void setDescription() {
        event.setDescription("An event for graduation ceremony");
        assertEquals(event.getDescription(), "An event for graduation ceremony");
        assertNotEquals(event.getDescription(), "An Event for graduation ceremony");
    }

    @Test
    void setOrganizer() {
        Organization organization = new Organization(
                2, "National Health Services",null,
                "0734634523623",
                "https://www.nhs.gov.uk/logo.png",
                "https://www.nhs.gov.uk",
                List.of(),
                List.of(),
                "#ffffff",
                "#fffff"
        );
        event.setOrganizer(organization);
        assertEquals(event.getOrganizer(), organization);
        init();
        assertNotEquals(event.getOrganizer(), organization);

    }


    @Test
    void setStart() {
        LocalDateTime start = LocalDateTime.of(2023,7,18,8,0);
        String startString = start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String startString2 = start.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
        String pattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        Matcher patternMatcher = Pattern.compile(pattern).matcher(startString);
        Matcher patternMatcher2 = Pattern.compile(pattern).matcher(startString2);
        event.setStart(start);
        assertEquals(event.getStart(), LocalDateTime.of(2023,7,18,8,0));
        init();
        assertNotEquals(event.getStart(), LocalDateTime.of(2023,7,18,8,0));
        assertTrue(patternMatcher.matches());
        assertFalse(patternMatcher2.matches());
    }

    @Test
    void setEnd() {
        LocalDateTime end = LocalDateTime.of(2023,7,18,12,0);
        String endString = end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String endString2 = end.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"));
        String pattern = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}";
        Matcher patternMatcher = Pattern.compile(pattern).matcher(endString);
        Matcher patternMatcher2 = Pattern.compile(pattern).matcher(endString2);
        event.setStart(end);
        assertEquals(event.getStart(), LocalDateTime.of(2023,7,18,12,0));
        init();
        assertNotEquals(event.getStart(), LocalDateTime.of(2023,7,18,12,0));
        assertTrue(patternMatcher.matches());
        assertFalse(patternMatcher2.matches());
    }

    @Test
    void addSpaces(){
        Space space = TestData.createSpace();
        event.addSpace(space);
        assertNotNull(event.getSpaces());
        assertFalse(event.getSpaces().isEmpty());
        assertEquals(event.getSpaces().get(0), space);
        assertEquals(space.getEvent(), event);
    }

    @Test
    void addSubSpaces(){
        Space space = TestData.createSpace();
        SubSpace subSpace = TestData.createSubSpace();
        space.add(subSpace);
        event.addSpace(space);
        event.addSubSpace(subSpace);
        assertNotNull(event.getSubSpaces());
        assertFalse(event.getSubSpaces().isEmpty());
        assertEquals(event.getSubSpaces().get(0), subSpace);
        assertEquals(subSpace.getEvent(), event);
    }



    @Test
    void testEquals() {
        Event similarEvent = event;
        Event differentEvent = new Event(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getOrganizer(),
                event.getSpaces(),
                event.getSubSpaces(),
                event.getStart(),
                event.getEnd(),
                event.getImageUrls()
        );
        assertEquals(event, similarEvent);
        assertEquals(event.hashCode(), similarEvent.hashCode());
        assertNotEquals(event, differentEvent);
        assertNotEquals(event.hashCode(), differentEvent.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(event.toString());
        assertFalse(event.toString().isEmpty());
    }
}