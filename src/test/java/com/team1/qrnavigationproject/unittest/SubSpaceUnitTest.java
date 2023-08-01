package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.model.SubSpace;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.team1.qrnavigationproject.model.Constant.IMAGE_URL_REGEX;
import static org.junit.jupiter.api.Assertions.*;

class SubSpaceUnitTest {

    private SubSpace subSpace;

    private void init(){
        subSpace = new SubSpace(
                1,
                "Abacws / 3.45",
                "room in Abacws",
                "",
                null,
                1
        );
        Space space = new Space(
                1,
                "Abacws Building",
                "school of computer science and informatics",
                "https://www.spaces.io/images/abacws.png",
                null,
                1,
                1,
                null
        );
        space.add(subSpace);
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
        int id = Integer.MAX_VALUE;
        subSpace.setId(id);
        assertEquals(subSpace.getId(), id);
        assertNotEquals(subSpace.getId(), 1);
    }

    @Test
    void getName() {
        String name = "Abacws / 3.65";
        subSpace.setName(name);
        assertEquals(subSpace.getName(), name);
        assertNotEquals(subSpace.getName(), "Abacws / 3.45");
    }

    @Test
    void getDescription() {
        String desc = "bookable room";
        subSpace.setDescription(desc);
        assertEquals(subSpace.getDescription(), desc);
        assertNotEquals(subSpace.getDescription(), "Room in Abacws");
    }

    @Test
    void getPhotoURL() {
        String photoURL = "https://www.cardiffuni.com/spaces/photos/abacws.png";
        Matcher URLMatcher = Pattern.compile(IMAGE_URL_REGEX).matcher(photoURL);
        subSpace.setPhotoURL(photoURL);
        assertEquals(subSpace.getPhotoURL(), photoURL);
        assertTrue(URLMatcher.matches());
        assertNotEquals(subSpace.getPhotoURL(), "https://www.cardiffuni.com/spaces/photo/abacws.png");
        photoURL="cardiffuni.com/spaces/photos/abacws.png";
        subSpace.setPhotoURL(photoURL);
        URLMatcher = Pattern.compile(IMAGE_URL_REGEX).matcher(subSpace.getPhotoURL());
        assertFalse(URLMatcher.matches());
        assertNotEquals(subSpace.getPhotoURL(), "https://www.cardiffuni.com/spaces/photo/abacws.png");

    }

    @Test
    void getSpace() {
        Space space = subSpace.getSpace();
        subSpace.setSpace(new Space());
        assertNotNull(space);
        assertEquals(subSpace, space.getSubSpaces().get(0));
        assertNotEquals(subSpace.getSpace(), space);


    }

    @Test
    void getTypeId() {
        int id = 100;
        subSpace.setTypeId(id);
        assertEquals(subSpace.getTypeId(), id);
        assertNotEquals(subSpace.getTypeId(), 1);
    }

    @Test
    void testEquals() {
        SubSpace similarSubspace = new SubSpace(
                subSpace.getId(),
                subSpace.getName(),
                subSpace.getDescription(),
                subSpace.getPhotoURL(),
                subSpace.getSpace(),
                subSpace.getTypeId()
        );
        assertEquals(subSpace, similarSubspace);
        assertEquals(subSpace.hashCode(), similarSubspace.hashCode());

    }

    @Test
    void testToString() {
        assertFalse(subSpace.toString().isEmpty());
        assertNotNull(subSpace.toString());
    }
}