


drop table if exists QRCode;
drop table if exists Content;
drop table if exists sub_space;
drop table if exists Space;
drop table if exists Event;
drop table if exists Space_type;
drop table if exists User_type;
drop table if exists Role;
drop table if exists `User`;
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
                             header_background varchar(9) default '#FFFFFF',
                             footer_background varchar(9) default '#FFFFFF',
                             foreign key(address_id) references Address(id)
);

create table `User`(
                       id int auto_increment primary key,
                       username varchar(60) unique,
                       password text,
                       age int,
                       org_id int,
                       is_account_expired bit,
                       is_credential_expired bit,
                       is_account_locked bit,
                       foreign key(org_id) references Organization(id)
);

create table Role(
                     id int auto_increment primary key,
                     `name` varchar(20),
                     user_id int,
                     foreign key(user_id) references `User`(id)
);

create table User_type(
                          id int auto_increment primary key,
                          `name` varchar(10),
                          user_id int,
                          foreign key(user_id) references `User`(id)
);

create table Event(
                      id int auto_increment primary key,
                      `name` varchar(30),
                      `description` text,
                      organizer int,
                      `start` datetime,
                      `end` datetime,
                      image_urls json null,
                      foreign key(organizer) references Organization(id)
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
                      photo_urls json null,
                      org_id int,
                      address_id int,
                      event_id int,
                      `type` int,
                      foreign key(org_id) references Organization(id),
                      foreign key(address_id) references Address(id),
                      foreign key(`type`) references Space_type(id),
                      foreign key (event_id) references Event(id)
);

create table sub_space(
                          id int auto_increment primary key,
                          `name` varchar(30),
                          `description` text,
                          photo_url text default null,
                          main_space int,
                          `type` int default null,
                          event_id int,
                          foreign key (event_id) references event(id),
                          foreign key(main_space) references Space(id),
                          foreign key(`type`) references Space_type(id)
);



create table Content(
                        id int auto_increment primary key,
                        name varchar(50),
                        description text,
                        event_id int,
                        space_id int,
                        sub_space_id int,
                        foreign key(event_id) references Event(id),
                        foreign key (space_id) references Space(id),
                        foreign key (sub_space_id) references sub_space(id)
);

create table QRCode(
                       id int auto_increment primary key,
                       content_id int,
                       space_id int,
                       sub_space_id int,
                       `description` text,
                       image_url text,
                       page_url text,
                       created_at datetime,
                       foreign key(space_id) references Space(id),
                       foreign key (content_id) references Content(id)
);


insert into Location(latitude, longitude)
values (51.4891719, -3.1811802);

insert into Address(description, location_id, postcode)
values ('Senghennydd Road', 1, 'CF24 4AG');

insert into Organization(name, address_id, phone, logo_url, website_url)
values ('Cardiff University', 1, '022972352373',
        'https://commons.wikimedia.org/wiki/File:Cardiff-university-vector-logo.svg', 'https://www.cardiff.ac.uk/');

insert into `User`(username, password, age, org_id, is_account_expired, is_credential_expired, is_account_locked)
values ('David', 'p@55w07d', 31, 1, 0, 0, 0);
insert into `User`(username, password, age, org_id, is_account_expired, is_credential_expired, is_account_locked)
values ('Benjamin', 'B@55w07d', 26, 1, 0, 0, 0);
insert into `User`(username, password, age, org_id, is_account_expired, is_credential_expired, is_account_locked)
values ('Derick', 'D@55w07d', 20, 1, 0, 0, 0);

insert into `User`(username, password, age, org_id, is_account_expired, is_credential_expired, is_account_locked)
values ('Ballamy', '5ec7et', 25, 1, 0, 0, 0);


insert into Role(name, user_id)
values ('SUPER_ADMIN', 1);

insert into Role(name, user_id)
values ('ADMIN', 2);

insert into Role(name, user_id)
values ('USER', 3);


insert into Role(name, user_id)
values ('USER', 4);

insert into User_type(name, user_id)
values ('Adult', 1);

insert into User_type(name, user_id)
values ('Adult', 2);

insert into User_type(name, user_id)
values ('Adult', 3);

insert into User_type(name, user_id)
values ('Adult', 4);

insert into Event(name, description, organizer, `start`, `end`,image_urls)
values ('Open day', 'Event for new students', 1, '2023-07-16 10:00:00', '2023-07-16 13:00:00','["imagefile1","imagefile2","imagefile3"]');


insert into Space_type(name)
values ('Building');

insert into Space_type(name)
values ('Park');

insert into Space_type(name)
values ('Countryside trail');

insert into Space(name, description, photo_urls, org_id, address_id, event_id, type)
values ('Abacws Building', 'Building for school of computer science and informatics', '["imagefile1","imagefile2","imagefile3"]', 1, 1, 1,  1);

insert into sub_space(name, description, photo_url, main_space,event_id)
values ('Room 3.65', 'Bookable room containing tables, chairs and white board',null, 1,1);


insert into Content(description, event_id)
values ('Content for open day event', 1);

insert into qrcode(content_id, space_id, sub_space_id, description, image_url,page_url, created_at)
values (1, 1, 1, 'QR code for room 3.45 @ Abacws building', 'img_url', 'https://www.qrnavigation.com/content', '2023-05-16 10:00:00');

select u.*,r.name as ROLE from `User` u right outer join Role r on u.id=r.id;

