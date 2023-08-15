package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.Location;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LocationUnitTest {

    private Location location;

    private void init() {
        location = TestData.createLocation();
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
        assertEquals(location.getId(), 1);
        assertNotEquals(location.getId(), 100);
    }

    @Test
    void getLatitude() {
        assertEquals(location.getLatitude(), 51.4891719);
        assertNotEquals(location.getLatitude(), 0.0000);
    }

    @Test
    void getLongitude() {
        assertEquals(location.getLongitude(), -3.1811802);
        assertNotEquals(location.getLongitude(), 0.0000);
    }

    @Test
    void setId() {
        location.setId(2);
        assertEquals(location.getId(), 2);
        init();
        assertNotEquals(location.getId(), 2);
    }

    @Test
    void setLatitude() {
        location.setLatitude(0.000000);
        assertEquals(location.getLatitude(), 0.000000);
        init();
        assertNotEquals(location.getLatitude(), 0.000000);
    }

    @Test
    void setLongitude() {
        location.setLongitude(0.000000);
        assertEquals(location.getLongitude(), 0.000000);
        init();
        assertNotEquals(location.getLongitude(), 0.000000);
    }

}

