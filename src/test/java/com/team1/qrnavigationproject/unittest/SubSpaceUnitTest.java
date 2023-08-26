//package com.team1.qrnavigationproject.unittest;
//
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
//import static com.team1.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;
//import static org.junit.jupiter.api.Assertions.*;
//
//class SubSpaceUnitTest {
//
//    private SubSpace subSpace;
//
//    private void init(){
//        subSpace = TestData.createSubSpace();
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
//        int id = Integer.MAX_VALUE;
//        subSpace.setId(id);
//        assertEquals(subSpace.getId(), id);
//        assertNotEquals(subSpace.getId(), 1);
//    }
//
//    @Test
//    void getName() {
//        String name = "Abacws / 3.65";
//        subSpace.setName(name);
//        assertEquals(subSpace.getName(), name);
//        assertNotEquals(subSpace.getName(), "Abacws / 3.45");
//    }
//
//    @Test
//    void getDescription() {
//        String desc = "bookable room";
//        subSpace.setDescription(desc);
//        assertEquals(subSpace.getDescription(), desc);
//        assertNotEquals(subSpace.getDescription(), "Room in Abacws");
//    }
//
//    @Test
//    void getPhotoURL() {
//        String photoURL = "https://www.cardiffuni.com/spaces/photos/abacws.png";
//        Matcher URLMatcher = Pattern.compile(IMAGE_URL_REGEX).matcher(photoURL);
//        subSpace.setPhotoURL(photoURL);
//        assertEquals(subSpace.getPhotoURL(), photoURL);
//        assertTrue(URLMatcher.matches());
//        assertNotEquals(subSpace.getPhotoURL(), "https://www.cardiffuni.com/spaces/photo/abacws.png");
//        photoURL="cardiffuni.com/spaces/photos/abacws.png";
//        subSpace.setPhotoURL(photoURL);
//        URLMatcher = Pattern.compile(IMAGE_URL_REGEX).matcher(subSpace.getPhotoURL());
//        assertFalse(URLMatcher.matches());
//        assertNotEquals(subSpace.getPhotoURL(), "https://www.cardiffuni.com/spaces/photo/abacws.png");
//
//    }
//
//    @Test
//    void getSpace() {
//        Space space = TestData.createSpace();
//        space.add(subSpace);
//        subSpace.setSpace(new Space());
//        assertNotNull(space);
//        assertTrue(space.getSubSpaces().contains(subSpace));
//
//
//    }
//
//    @Test
//    void getTypeId() {
//        int id = 100;
//        subSpace.setTypeId(id);
//        assertEquals(subSpace.getTypeId(), id);
//        assertNotEquals(subSpace.getTypeId(), 1);
//    }
//
//    @Test
//    void testToString() {
//        assertFalse(subSpace.toString().isEmpty());
//        assertNotNull(subSpace.toString());
//    }
//}