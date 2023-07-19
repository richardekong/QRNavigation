package com.example.qrnavigationproject.unittest;

import com.example.qrnavigationproject.model.Address;
import com.example.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class AddressUnitTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = TestData.createAddress();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        assertEquals(address.getId(), 1);
        assertNotEquals(address.getId(), -1);
    }

    @Test
    void getDescription() {
        assertEquals(address.getDescription(), "Abacws Building, Senghennydd Road");
        assertNotEquals(address.getDescription(), "Sparc Building 4AG");
    }

    @Test
    void getLocationId() {
        assertEquals(address.getLocationId(), 1);
        assertNotEquals(address.getLocationId(), 10);
    }

    @Test
    void getPostcode() {
        assertEquals(address.getPostcode(), "CF24 4AG");
        assertNotEquals(address.getPostcode(), "CF24 4BF");
    }

    @Test
    void setId() {
        int id = 100;
        address.setId(id);
        assertEquals(address.getId(), 100);
        assertNotEquals(address.getId(), 0);
    }

    @Test
    void setDescription() {
        String desc = "Sparc Building, Maindy Road";
        address.setDescription(desc);
        assertEquals(address.getDescription(), "Sparc Building, Maindy Road");
        assertNotEquals(address.getDescription(), "Sparc Building, Column Road");

    }

    @Test
    void setLocationId() {
        int id = 300;
        address.setLocationId(id);
        assertEquals(address.getLocationId(), 300);
        assertNotEquals(address.getLocationId(), 301);
    }

    @Test
    void setPostcode() {
        String postCode = "CF24 4DS";
        address.setPostcode(postCode);
        assertEquals(address.getPostcode(), "CF24 4DS");
        assertNotEquals(address.getPostcode(), "CF25 4DS");
    }

    @Test
    void testEquals() {
        Address address1 = new Address(
                address.getId(),
                address.getDescription(),
                address.getLocationId(),
                address.getPostcode()
        );

        assertEquals(address1, address);
        address.setId(5);
        assertNotEquals(address1, address);
    }

    @Test
    void testHashCode() {
        int hashCode = 315331412;
        assertEquals(address.hashCode(), hashCode);
        assertNotEquals(address.hashCode(), 316252928);
    }

    @Test
    void testToString() {
        String addressString = "Address(id=1, description=Abacws Building, " +
                "Senghennydd Road, locationId=1, postcode=CF24 4AG)";
        assertEquals(address.toString(), addressString);
        address.setId(5);
        assertNotEquals(address.toString(), "Address(id=1, description=Abacws" +
                " Building, Senghennydd Road, locationId=1, postcode=CF24 4AG)");
        assertNotNull(address.toString());
    }

    @Test
    void validateAddressPostCode() {
        String postCode0 = "CF24 4DS",
                postCode1 = "34CF 4Ds";
        address.setPostcode(postCode0);
        Pattern pattern = Pattern.compile("^[A-Z]{1,2}[\\dR][\\dA-Z]? \\d[A-Z]{2}$");
        Matcher matcher1 = pattern.matcher(address.getPostcode());
        address.setPostcode(postCode1);
        Matcher matcher2 = pattern.matcher(address.getPostcode());
        assertTrue(matcher1.matches());
        assertFalse(matcher2.matches());
    }
}