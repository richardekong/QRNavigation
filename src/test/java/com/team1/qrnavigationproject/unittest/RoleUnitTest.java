package com.team1.qrnavigationproject.unittest;

import com.team1.qrnavigationproject.model.Role;
import com.team1.qrnavigationproject.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleUnitTest {

    private List<Role> roles;
    private Role admin;
    private Role superAdmin;
    private Role userRole;

    private void init() {
        superAdmin = new Role(1, "SUPER_ADMIN", null);
        admin = new Role(2, "ADMIN", null);
        userRole = new Role(3, "USER", null);
        User david = new User(
                1,
                "david",
                "password",
                20,
                1,
                null,
                null, 1, false, false, false, true);
        User matt = new User(
                1,
                "Matt",
                "password",
                21,
                1,
                null,
                null, 1, false, false, false, true);
        david.add(superAdmin);
        david.add(admin);
        matt.add(userRole);
        roles = new LinkedList<>();
        roles.addAll(david.getRoles());
        roles.addAll(matt.getRoles());
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
        int id = 100;
        admin.setId(id);
        assertEquals(admin.getId(), id);
        assertNotEquals(admin.getId(), 1);
    }

    @Test
    void getName() {
        String name = "Alpha";
        superAdmin.setName(name);
        assertEquals(superAdmin.getName(), name);
        assertNotEquals(superAdmin.getName(), "SUPER_ADMIN");
    }

    @Test
    void getUser() {
        User userAssignedUserRole = userRole.getUser();
        assertNotNull(userAssignedUserRole);
        assertEquals(userAssignedUserRole, roles.get(2).getUser());
    }

    @Test
    void setId() {
        int id = 2;
        userRole.setId(id);
        assertEquals(userRole.getId(), id);
        assertNotEquals(userRole.getId(), 3);
    }

    @Test
    void testEquals() {
        Role anotherSuperAdmin = new Role(
                superAdmin.getId(),
                superAdmin.getName(),
                superAdmin.getUser()
        );
        assertEquals(superAdmin, anotherSuperAdmin);
        assertEquals(superAdmin.hashCode(), anotherSuperAdmin.hashCode());
        anotherSuperAdmin.setId(50);
        assertNotEquals(superAdmin, anotherSuperAdmin);
        assertNotEquals(superAdmin.hashCode(), anotherSuperAdmin.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(userRole.toString());
        assertNotNull(admin.toString());
        assertNotNull(superAdmin.toString());
        System.out.println(superAdmin.toString());
    }
}