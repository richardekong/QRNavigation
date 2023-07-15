
drop table if exists QRCode;
drop table if exists Content;
drop table if exists Event;
drop table if exists sub_space;
drop table if exists Space;
drop table if exists Space_type;
drop table if exists `User`;
drop table if exists User_type;
drop table if exists Role;
drop table if exists Organization;
drop table if exists Address;
drop table if exists Location;

create table Location(
                         id int auto_increment primary key,
                         latitude decimal(10,2),
                         longitude decimal(10,2)
);

create table Address(
                        id int auto_increment primary key,
                        `description` text,
                        location_id int,
                        postcode varchar(10),
                        foreign key(location_id) references Location(id)
);

create table Organization(
                             id int auto_increment primary key,
                             `name` varchar(60) unique,
                             address_id int,
                             phone varchar(15),
                             logo_url text,
                             website_url text,
                             foreign key(address_id) references Address(id)
);

create table Role(
                     id int auto_increment primary key,
                     `name` varchar(20) unique
);

create table User_type(
                          id int auto_increment primary key,
                          `name` varchar(10) unique
);

create table `User`(
                       id int auto_increment primary key,
                       username varchar(60) unique,
                       password text,
                       age int,
                       org_id int,
                       role_id int,
                       type_id int,
                       is_account_expired bit,
                       is_credential_expired bit,
                       is_account_locked bit,
                       foreign key(org_id) references Organization(id),
                       foreign key(type_id) references User_type(id),
                       foreign key(role_id) references Role(id)
);

-- Space_type presents the type of space which could be a Building, park, or country side trail
create table Space_type(
                           id int auto_increment primary key,
                           `name` varchar(30)
);

create table Space(
                      id int auto_increment primary key,
                      `name` varchar(30) unique not null,
                      `description` text,
                      org_id int,
                      address_id int,
                      `type` int,
                      foreign key(org_id) references Organization(id),
                      foreign key(address_id) references Address(id),
                      foreign key(`type`) references Space_type(id)
);

create table sub_space(
                          id int auto_increment primary key,
                          `name` varchar(30),
                          `description` text,
                          main_space int,
                          foreign key(main_space) references Space(id)
);

create table Event(
                      id int auto_increment primary key,
                      `name` varchar(30),
                      `description` text,
                      organizer int,
                      space_id int,
                      `start` datetime,
                      `end` datetime,
                      foreign key(organizer) references `User`(id),
                      foreign key(space_id) references Space(id)
);

create table Content(
                        id int auto_increment primary key,
                        description text,
                        page_url text,
                        event_id int,
                        foreign key(event_id) references Event(id)
);

create table QRCode(
                       id int auto_increment primary key,
                       content_id int,
                       space_id int,
                       sub_space_id int,
                       `description` text,
                       image_url text,
                       is_scanned bit,
                       scanned_at datetime,
                       created_at datetime,
                       foreign key(space_id) references Space(id),
                       foreign key (content_id) references Content(id)
);

