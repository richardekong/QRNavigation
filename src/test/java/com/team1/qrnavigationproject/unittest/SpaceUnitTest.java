//package com.team1.qrnavigationproject.unittest;
//
//import com.team1.qrnavigationproject.model.Constant;
//import com.team1.qrnavigationproject.model.Event;
//import com.team1.qrnavigationproject.model.Space;
//import com.team1.qrnavigationproject.model.SubSpace;
//import com.team1.qrnavigationproject.stub.TestData;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import static org.junit.jupiter.api.Assertions.*;
//class SpaceUnitTest {
//
//    private Space space;
//
//    private void init(){
//        Event event = TestData.createEvent();
//        space = new Space(
//                1,
//                "Abacws Building",
//                "school of computer science and informatics",
//                "https://www.spaces.io/images/abacws.png",
//                null,
//                1,
//                1,
//                null,
//                event
//        );
//        SubSpace subSpace = new SubSpace(
//                1, "Abacws / 3.45", "room in Abacws", "", null,event,  1
//        );
//        space.add(subSpace);
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
//    void testId() {
//        int id = Integer.MAX_VALUE;
//        space.setId(id);
//        assertEquals(space.getId(), id);
//        assertNotEquals(space.getId(), 1);
//    }
//
//    @Test
//    void testName() {
//        String name = "Queens Building";
//        space.setName(name);
//        assertEquals(space.getName(), name);
//        assertNotEquals(space.getName(), "Abacws Building");
//    }
//
//    @Test
//    void testDescription() {
//        String desc = "Cardiff University Engineering Building";
//        assertNotNull(space.getDescription());
//        assertFalse(space.getDescription().isEmpty() || space.getDescription().isBlank());
//        space.setDescription(desc);
//        assertEquals(space.getDescription(), desc);
//        assertNotEquals(space.getDescription(), "school of computer science and informatics");
//    }
//
//    @Test
//    void testPhotoURL() {
//        String URLPattern = Constant.IMAGE_URL_REGEX;
//        Matcher URLMatcher = Pattern.compile(URLPattern).matcher(space.getPhotoURLs());
//        assertEquals(space.getPhotoURLs(), "https://www.spaces.io/images/abacws.png");
//        assertTrue(URLMatcher.matches());
//        space.setPhotoURLs("www.cardiffuni.com/logo.png");
//        URLMatcher = Pattern.compile(URLPattern).matcher(space.getPhotoURLs());
//        assertNotEquals(space.getPhotoURLs(), "http://www.cardiffuni.com/logo.png");
//        assertFalse(URLMatcher.matches());
//    }
//
//    @Test
//    void testAddressId() {
//        int id = 2;
//        space.setAddressId(id);
//        assertEquals(space.getAddressId(), id);
//        assertNotEquals(space.getAddressId(), 1);
//    }
//
//    @Test
//    void testTypeId() {
//        int id = 2;
//        space.setTypeId(id);
//        assertEquals(space.getTypeId(), id);
//        assertNotEquals(space.getTypeId(), 1);
//    }
//
//    @Test
//    void testSubSpaces() {
//        SubSpace subSpace = space.getSubSpaces().get(0);
//        assertNotNull(space.getSubSpaces());
//        assertTrue(space.getSubSpaces().size() > 0);
//        assertNotNull(subSpace);
//        assertEquals(subSpace.getSpace(), space);
//    }
//
//}