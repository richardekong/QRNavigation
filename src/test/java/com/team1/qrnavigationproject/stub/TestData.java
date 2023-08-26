package com.team1.qrnavigationproject.stub;

import com.team1.qrnavigationproject.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;

public interface TestData {

    static Address createAddress() {
        return new Address(1, "Abacws Building, Senghennydd Road", new Location(), "CF24 4AG", new Organization());
    }

    static Content createContent() {
        return new Content(1, "open day content", "Content page for Open day"
                , null, null, null);
    }

    static Event createEvent() {
        return new Event(
                 1,
                 "Open day",
                 "An event for open day",
                new Organization(),
                 LocalDateTime.of(2023, 7, 10, 0, 0),
                 LocalDateTime.of(2023, 7, 18, 0, 0),
                "event1/img1"
         );
    }

    static Location createLocation() {
        return new Location(
                1,
                51.4891719,
                -3.1811802,
                new Address()
        );
    }

    static Organization createOrganization() {
        return new Organization(
                1,
                "Cardiff University",
                null,
                "0809723723",
                "https://www.cardiffuni.com/logo.png",
                "https://www.cardiffuni.com",
                new ArrayList<>(),
                new ArrayList<>(),
                new User(),
                "#FFFFFF",
                "#FFFFFF"
        );

    }

    static QRCode createQRCode() {
        return new QRCode(
                1,
                "QRCode for Abacws / 3.45",
                null,
                null,
                "https://www.cardiffuni.com",
                "https://www.cardiffuni.com/qr.png",
                LocalDateTime.of(2023, 7, 15, 14, 5, 0)
        );
    }

    static LinkedList<Role> createRoles() {
       return new LinkedList<>();
    }

    static Role createSuperAdmin() {
        return new Role(1, "SUPER_ADMIN", new User());
    }

    static Role createAdmin() {
        return new Role(2, "ADMIN", new User());
    }

    static Role createUserRole() {
        return new Role(3, "USER", new User());
    }

    static User createDavid(){
        return new User(
                1,
                "david",
                "password",
                20,
                new Organization(),
                new ArrayList<>(),
                new ArrayList<>(),
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
                new Organization(),
                new ArrayList<>(),
                new ArrayList<>(),
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
        Space space =  new Space(
                1,
                "Abacws Building",
                "school of computer science and informatics",
                "https://www.spaces.io/images/abacws.png",
               new Organization(),
                1,
                1,
                new ArrayList<>()
        );
        space.add(createSubSpace());
        return space;
    }

    static SubSpace createSubSpace(){
        return  new SubSpace(
                1, "3.45", "room in Abacws", "img/1", new Space(),1
        );
    }

    static UserType createChild() {
        return new UserType(1, "Child", new User());
    }

    static UserType createAdult(){
        return new UserType(2, "Adult", new User());
    }

}
