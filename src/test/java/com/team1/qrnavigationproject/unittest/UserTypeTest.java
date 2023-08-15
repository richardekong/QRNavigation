package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.User;
import com.team1.qrnavigationproject.model.UserType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTypeTest {
    private UserType child;
    private void init() {
        child = new UserType(1, "Child", null);
        User xamy = new User(
                1,
                "Xamy",
                "password",
                1,
                null,
                null,
                null, false, false, false, true);
        xamy.add(child);
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
    void testId() {
        int id = 34;
        child.setId(id);
        assertEquals(child.getId(), id);
        assertNotEquals(child.getId(), 1);
    }

    @Test
    void testName() {
        String name = "Adult";
        child.setName(name);
        assertEquals(child.getName(), name);
        assertNotEquals(child.getName(), "Child");
    }

    @Test
    void getUser() {
        User user = child.getUser();
        User differentUser = new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getAge(),
                user.getOrganization(),
                user.getRoles(),
                user.getUserTypes(),
                user.isAccountExpired(),
                user.isCredentialExpired(),
                user.isAccountLocked(),
                user.isAccountEnabled()
        );
        assertNotNull(user);
        assertEquals(child, user.getUserTypes().get(0));
        differentUser.setAge(10);
        assertNotEquals(user, differentUser);
    }

    @Test
    void testEquals() {
        UserType similarChild = new UserType(
                child.getId(),
                child.getName(),
                child.getUser()
        );
        UserType adult = new UserType(
                2,
                child.getName(),
                child.getUser()
        );
        adult.getUser().setAge(18);
        assertEquals(child, similarChild);
        assertEquals(child.hashCode(), child.hashCode());
        assertNotEquals(child, adult);
        assertNotEquals(child.hashCode(), adult.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(child.toString());
        assertFalse(child.toString().isEmpty());
    }
}