package com.team1.qrnavigationproject.stub;

import com.example.qrnavigationproject.model.*;
import com.team1.qrnavigationproject.model.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public interface TestData {

    static Address createAddress() {
        return new Address(1, "Abacws Building, Senghennydd Road", 1, "CF24 4AG");
    }

    static Content createContent() {
        return new Content(1, "Content page for Open day",
                "https:www.content.com", 1, 1, 1);
    }

    static Event createEvent() {
        return new Event(
                1, "Open day", "An event for open day",
                new Organization(
                        1,
                        "Cardiff University",
                        1,
                        "0809723723",
                        "https://www.cardiffuni.com/logo.png",
                        "https://www.cardiffuni.com",
                        List.of(),
                        List.of()
                ),
                1,
                LocalDateTime.of(2023, 7, 10, 0, 0),
                LocalDateTime.of(2023, 7, 18, 0, 0)
        );
    }

    static Location createLocation() {
        return new Location(
                1,
                51.4891719,
                -3.1811802
        );
    }

    static Organization createOrganization() {
        return new Organization(
                1,
                "Cardiff University",
                1,
                "0809723723",
                "https://www.cardiffuni.com/logo.png",
                "https://www.cardiffuni.com",
                null,
                null
        );
    }

    static QRCode createQRCode() {
        return new QRCode(
                1,
                "QRCode for Abacws / 3.45",
                1,
                1,
                1,
                "https://www.cardiffuni.com/logo.png",
                false,
                LocalDateTime.of(2023, 7, 15, 14, 5, 0),
                LocalDateTime.of(2023, 7, 15, 14, 5, 0)
        );
    }

    static LinkedList<Role> createRoles() {
       return new LinkedList<>();
    }

    static Role createSuperAdmin() {
        return new Role(1, "SUPER_ADMIN", null);
    }

    static Role createAdmin() {
        return new Role(2, "ADMIN", null);
    }

    static Role createUserRole() {
        return new Role(3, "USER", null);
    }

    static User createDavid(){
        return new User(
                1,
                "david",
                "password",
                20,
                1,
                null,
                null,
                1,
                false,
                false,
                false,
                true);
    }

    static User createMatt(){
        return new User(
                1,
                "Matt",
                "password",
                21,
                1,
                null,
                null,
                1,
                false,
                false,
                false,
                true);
    }

    static SpaceType createBuilding(){
        return new SpaceType(1, "Building");
    }

    static SpaceType createPark(){
        return new SpaceType(2, "Park");
    }

    static SpaceType createCountrySideTrail(){
        return new SpaceType(3, "CountrySide Trail");
    }

    static Space createSpace(){
        return new Space(
                1,
                "Abacws Building",
                "school of computer science and informatics",
                "https://www.spaces.io/images/abacws.png",
                null,
                1,
                1,
                null
        );
    }

    static SubSpace createSubSpace(){
        return  new SubSpace(
                1, "Abacws / 3.45", "room in Abacws", "", null, 1
        );
    }

    static UserType createChild() {
        return new UserType(1, "Child", null);
    }

    static UserType createAdult(){
        return new UserType(2, "Adult", null);
    }

}
