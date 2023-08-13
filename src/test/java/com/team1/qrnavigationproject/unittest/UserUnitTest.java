package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUnitTest {

    private User sammy;

    private void init() {
        sammy = new User(
                1,
                "Sammy",
                "password",
                21,
                null,
                null,
                null,
                false,
                false,
                false,
                true
        );
        Role userRole = new Role(
                3,
                "USER",
                null
        );
        UserType adult = new UserType(
                1,
                "Adult",
                null
        );
        sammy.add(userRole);
        sammy.add(adult);
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
    void getAuthorities() {
        String authoritiesString = sammy.getAuthorities().toString();
        assertNotNull(authoritiesString);
        assertEquals(sammy.getAuthorities().toString(), authoritiesString);
        sammy.add(new Role(2, "ADMIN", null));
        assertNotEquals(sammy.getAuthorities().toString(), authoritiesString);
    }

    @Test
    void getPassword() {
        String password = "5ecret";
        sammy.setPassword(password);
        assertEquals(sammy.getPassword(), password);
        assertNotEquals(sammy.getPassword(), "password");
    }

    @Test
    void getUsername() {
        String username = "Sammy@boss";
        sammy.setUsername(username);
        assertEquals(sammy.getUsername(), username);
        assertNotEquals(sammy.getUsername(), "Sammy");
    }

    @Test
    void testAccountStatus() {
        assertFalse(sammy.isAccountExpired());
        assertFalse(sammy.isAccountLocked());
        assertFalse(sammy.isCredentialsNonExpired());
        assertTrue(sammy.isAccountEnabled());

    }

    @Test
    void getId() {
        int id = 9;
        sammy.setId(id);
        assertEquals(sammy.getId(), id);
        assertNotEquals(sammy.getId(), 1);
    }

    @Test
    void getAge() {
        int age = sammy.getAge() + 1;
        sammy.setAge(age);
        assertEquals(sammy.getAge(), 22);
        assertNotEquals(sammy.getAge(), 21);
    }

    @Test
    void getOrganization() {
        sammy.setOrganization(TestData.createOrganization());
        int organizationId = sammy.getOrganization().getId();
        sammy.getOrganization().setId(organizationId);
        assertEquals(sammy.getOrganization().getId(), organizationId);
        assertNotEquals(sammy.getOrganization().getId(), Integer.MAX_VALUE);
    }

    @Test
    void getRoles() {
        Role expectedRole = new Role(3, "USER", sammy);
        assertNotNull(sammy.getRoles());
        assertFalse(sammy.getRoles().isEmpty());
        assertTrue(sammy.getRoles().contains(expectedRole));
    }

    @Test
    void getUserTypes() {
        UserType expectedUserType = new UserType(1, "Adult", sammy);
        assertNotNull(sammy.getUserTypes());
        assertFalse(sammy.getRoles().isEmpty());
        assertTrue(sammy.getUserTypes().contains(expectedUserType));
    }

    @Test
    void getTypeId() {
        int id = 2;
        sammy.getUserTypes().get(0).setId(id);
        assertEquals(sammy.getUserTypes().get(0).getId(), id);
        assertNotEquals(sammy.getUserTypes().get(0).getId(), 1);
    }

    @Test
    void testEquals() {
        User expectedUser0 = new User(
                sammy.getId(),
                sammy.getUsername(),
                sammy.getPassword(),
                sammy.getAge(),
                sammy.getOrganization(),
                sammy.getRoles(),
                sammy.getUserTypes(),
                sammy.isAccountExpired(),
                sammy.isCredentialExpired(),
                sammy.isAccountLocked(),
                sammy.isAccountEnabled()
        );
        User expectedUser1 = sammy;
        assertEquals(sammy, expectedUser1);
        assertEquals(sammy.hashCode(), expectedUser1.hashCode());
        assertNotEquals(expectedUser0, expectedUser1);
        assertNotEquals(expectedUser0.hashCode(), expectedUser1.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(sammy.toString());
        assertFalse(sammy.toString().isEmpty());
    }
}

