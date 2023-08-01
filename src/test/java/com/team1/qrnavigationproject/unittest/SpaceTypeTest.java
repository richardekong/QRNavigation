package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.SpaceType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTypeTest {

    private SpaceType spaceType;

    private void init() {
        spaceType = new SpaceType(1, "Building");
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
        int id = 5;
        spaceType.setId(id);
        assertEquals(spaceType.getId(), id);
        assertNotEquals(spaceType.getId(), 1);
    }

    @Test
    void getName() {
        String name = "Park";
        spaceType.setName(name);
        assertEquals(spaceType.getName(), name);
        assertNotEquals(spaceType.getName(),"Building");
    }

    @Test
    void testEquals() {
        SpaceType similarTypeOfSpace = new SpaceType(
                spaceType.getId(), spaceType.getName()
        );
        assertEquals(spaceType, similarTypeOfSpace);
        assertEquals(spaceType.hashCode(), similarTypeOfSpace.hashCode());
        similarTypeOfSpace.setName("Countryside Trail");
        assertNotEquals(spaceType, similarTypeOfSpace);
        assertNotEquals(spaceType.hashCode(), similarTypeOfSpace.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(spaceType.toString());
        assertFalse(spaceType.toString().isEmpty());
    }
}