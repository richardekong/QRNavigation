//package com.team1.qrnavigationproject.unittest;
//
//import com.team1.qrnavigationproject.model.Event;
//import com.team1.qrnavigationproject.model.Organization;
//import com.team1.qrnavigationproject.model.Space;
//import com.team1.qrnavigationproject.stub.TestData;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class OrganizationUnitTest {
//
//    private Organization organization;
//
//    private void init() {
//        organization = TestData.createOrganization();
//        organization.setAddress(TestData.createAddress());
//        organization.add(TestData.createEvent());
//        organization.add(TestData.createSpace());
//    }
//
//    @BeforeEach
//    void setUp() {
//        init();
//    }
//
//    @AfterEach
//    void tearDown() {
//        init();
//    }
//
//    @Test
//    void getId() {
//        assertEquals(organization.getId(), 1);
//        assertNotEquals(organization.getId(), 100);
//    }
//
//    @Test
//    void getName() {
//        assertEquals(organization.getName(), "Cardiff University");
//        assertNotEquals(organization.getName(), "Cardiff Met");
//    }
//
//    @Test
//    void getAddress() {
//        assertEquals(organization.getAddress().getId(), 1);
//        assertNotEquals(organization.getAddress().getId(), 100);
//    }
//
//    @Test
//    void getPhone() {
//        assertEquals(organization.getPhone(), "0809723723");
//        assertNotEquals(organization.getPhone(), "0809023723");
//    }
//
//    @Test
//    void getLogoURL() {
//        String URLPattern = "^(https?|ftp)://[^\\s/$.?#].\\S*$";
//        Matcher URLMatcher = Pattern.compile(URLPattern).matcher(organization.getLogoURL());
//        assertEquals(organization.getLogoURL(), "https://www.cardiffuni.com/logo.png");
//        assertTrue(URLMatcher.matches());
//        organization.setLogoURL("www.cardiffuni.com/logo.png");
//        URLMatcher = Pattern.compile(URLPattern).matcher(organization.getLogoURL());
//        assertNotEquals(organization.getLogoURL(), "http://www.cardiffuni.com/logo.png");
//        assertFalse(URLMatcher.matches());
//    }
//
//    @Test
//    void getWebsiteURL() {
//        String URLPattern = "^https?://(?:www\\.)?[\\w\\d-]+(?:\\.[\\w\\d.-]+)*(?:/\\S*)?$";
//        Matcher URLMatcher = Pattern.compile(URLPattern).matcher(organization.getWebsiteURL());
//        assertEquals(organization.getWebsiteURL(), "https://www.cardiffuni.com");
//        assertTrue(URLMatcher.matches());
//        organization.setWebsiteURL("www.cardiffuni.com");
//        URLMatcher = Pattern.compile(URLPattern).matcher(organization.getWebsiteURL());
//        assertNotEquals(organization.getWebsiteURL(), "http://www.cardiffuni.com");
//        assertFalse(URLMatcher.matches());
//    }
//
//    @Test
//    void getSpaces() {
//        assertNotNull(organization.getSpaces());
//        assertFalse(organization.getSpaces().isEmpty());
//        assertEquals(organization.getSpaces().size(), 1);
//
//    }
//
//    @Test
//    void getEvents() {
//        assertNotNull(organization.getEvents());
//        assertFalse(organization.getEvents().isEmpty());
//        assertEquals(organization.getEvents().size(), 1);
//    }
//
//    @Test
//    void setId() {
//        organization.setId(2);
//        assertEquals(organization.getId(), 2);
//        assertNotEquals(organization.getId(), 1);
//    }
//
//    @Test
//    void setName() {
//        String name = "National Health Services";
//        organization.setName(name);
//        assertEquals(organization.getName(), "National Health Services");
//        assertNotEquals(organization.getName(), "National Healthcare Services");
//    }
//
//    @Test
//    void setAddress() {
//        int id = 3;
//        organization.getAddress().setId(id);
//        assertEquals(organization.getAddress().getId(), id);
//        assertNotEquals(organization.getAddress().getId(), 1);
//    }
//
//    @Test
//    void setPhone() {
//        String phone = "0834634532";
//        organization.setPhone(phone);
//        assertEquals(organization.getPhone(), "0834634532");
//        assertNotEquals(organization.getPhone(), "0834634531");
//    }
//
//    @Test
//    void setLogoURL() {
//        String URLRegex = "^(https?|ftp)://[^\\s/$.?#].\\S*$";
//        String URL = "www.cardiffuni.org/logo.png";
//        Matcher URLMatcher = Pattern
//                .compile(URLRegex)
//                .matcher(URL);
//        organization.setLogoURL(URL);
//        assertEquals(organization.getLogoURL(), "www.cardiffuni.org/logo.png");
//        assertNotEquals(organization.getLogoURL(), "https://www.cardiffuni.org/logo.png");
//        assertFalse(URLMatcher.matches());
//        organization.setLogoURL("https://www.cardiffuni.org/logo.png");
//        URLMatcher = Pattern
//                .compile(URLRegex)
//                .matcher(organization.getLogoURL());
//        assertTrue(URLMatcher.matches());
//    }
//
//    @Test
//    void setWebsiteURL() {
//        String URLRegex = "^(https?|ftp)://[^\\s/$.?#].\\S*$";
//        String URL = "www.cardiffuni.org";
//        Matcher URLMatcher = Pattern
//                .compile(URLRegex)
//                .matcher(URL);
//        organization.setWebsiteURL(URL);
//        assertEquals(organization.getWebsiteURL(), "www.cardiffuni.org");
//        assertNotEquals(organization.getWebsiteURL(), "https://www.cardiffuni.org");
//        assertFalse(URLMatcher.matches());
//        organization.setWebsiteURL("https://www.cardiffuni.org");
//        URLMatcher = Pattern
//                .compile(URLRegex)
//                .matcher(organization.getWebsiteURL());
//        assertTrue(URLMatcher.matches());
//    }
//
//    @Test
//    void setSpaces() {
//        List<Space> emptySpace = new ArrayList<>();
//        organization.setSpaces(emptySpace);
//        assertEquals(organization.getSpaces(), new ArrayList<Space>());
//        assertTrue(organization.getSpaces().containsAll(emptySpace));
//        emptySpace.add(new Space());
//        assertFalse(organization.getSpaces().isEmpty());
//    }
//
//    @Test
//    void setEvents() {
//        List<Event> events = new ArrayList<>();
//        organization.setEvents(events);
//        assertEquals(organization.getEvents(), new ArrayList<Event>());
//        assertTrue(organization.getEvents().containsAll(events));
//        events.add(new Event());
//        assertFalse(organization.getEvents().isEmpty());
//    }
//
//}