create database if not exists c22106964_qrnavigation;

use c22106964_qrnavigation;


drop table if exists qrcode;
drop table if exists content;
drop table if exists subspace;
drop table if exists space;
drop table if exists space_type;
drop table if exists event;
drop table if exists user_type;
drop table if exists role;
drop table if exists app_user;
drop table if exists organization;
drop table if exists address;
drop table if exists location;

create table location
(
    id        int auto_increment primary key,
    latitude  decimal(10,6 ),
    longitude decimal(10,6)
);

create table address
(
    id            int auto_increment primary key,
    `description` text,
    location_id   int default null,
    postcode      varchar(10),
    foreign key (location_id) references location (id)
);

create table organization
(
    id                int auto_increment primary key,
    `name`            varchar(60) unique,
    address_id        int        default null,
    phone             varchar(15),
    logo_url          text,
    website_url       text,
    header_background varchar(9) default '#FFFFFF',
    footer_background varchar(9) default '#FFFFFF',
    foreign key (address_id) references address (id)
);

create table app_user
(
    id                    int auto_increment primary key,
    username              varchar(60) unique,
    password              text,
    age                   int,
    organization_id       int default null,
    is_account_expired    bit,
    is_credential_expired bit,
    is_account_locked     bit,
    is_account_enabled    bit,
    foreign key (organization_id) references organization (id)
);

create table role
(
    id      int auto_increment primary key,
    `name`  varchar(20),
    user_id int default null,
    foreign key (user_id) references app_user (id)
);

create table user_type
(
    id      int auto_increment primary key,
    `name`  varchar(10),
    user_id int default null,
    foreign key (user_id) references app_user (id)
);

create table event
(
    id            int auto_increment primary key,
    `name`        varchar(30),
    `description` text,
    organizer     int  default null,
    `start`       datetime,
    `end`         datetime,
    image_urls    json default '[]',
    foreign key (organizer) references organization (id)
);

-- Space_type presents the type of space which could be a Building, park, or country side trail
create table space_type
(
    id     int auto_increment primary key,
    `name` varchar(30)
);

create table space
(
    id            int auto_increment primary key,
    `name`        varchar(30) unique not null,
    `description` text,
    photo_urls    json default null,
    org_id        int default null,
    address_id    int default null,
    type_id       int default null,
    foreign key (org_id) references organization (id),
    foreign key (address_id) references address (id),
    foreign key (type_id) references space_type (id)
);

create table subspace
(
    id            int auto_increment primary key,
    `name`        varchar(30),
    `description` text,
    photo_url     text default null,
    main_space    int  default null,
    `type`       int  default null,
    foreign key (main_space) references space (id),
    foreign key (`type`) references space_type (id)
);



create table content
(
    id            int auto_increment primary key,
    `name`        varchar(50),
    `description` text,
    event_id      int default null,
    space_id      int default null,
    subspace_id   int default null,
    foreign key (event_id) references event (id),
    foreign key (space_id) references space (id),
    foreign key (subspace_id) references subspace (id)
);

create table qrcode
(
    id            int auto_increment primary key,
    space_id      int default null,
    sub_space_id  int default null,
    `description` text,
    image_url     text,
    page_url      text,
    created_at    datetime,
    foreign key (space_id) references space (id),
    foreign key (sub_space_id) references subspace (id)
);


insert into location(latitude, longitude)
values (51.4891719, -3.1811802);

insert into location(latitude, longitude)
values (51.4891719, -3.1811802);

insert into location(latitude, longitude)
values (51.4891719, -3.1811802);

insert into address(description, location_id, postcode)
values ('Abacws, Senghennydd Road', 1, 'CF24 4AG');

insert into address(description, location_id, postcode)
values ('Cardiff Student Union, Senghennydd Road', 2, 'CF24 4AG');

insert into address(description, location_id, postcode)
values ('Salisbury Road', 3, 'CF24 4DS');


insert into organization(name, address_id, phone, logo_url, website_url)
values ('Fake Cardiff University', 1, '022972352373',
        'https://commons.wikimedia.org/wiki/File:Cardiff-university-vector-logo.svg', 'https://www.cardiff.ac.uk/');

insert into app_user(username, password, age, organization_id, is_account_expired, is_credential_expired, is_account_locked,is_account_enabled)
values ('David', 'p@55w07d', 31, 1, 0, 0, 0, 0);
insert into app_user(username, password, age, organization_id, is_account_expired, is_credential_expired, is_account_locked,is_account_enabled)
values ('Benjamin', 'B@55w07d', 26, 1, 0, 0, 0, 0);
insert into app_user(username, password, age, organization_id, is_account_expired, is_credential_expired, is_account_locked,is_account_enabled)
values ('Derick', 'D@55w07d', 20, 1,  0, 0, 0, 0);

insert into app_user(username, password, age, organization_id, is_account_expired, is_credential_expired, is_account_locked,is_account_enabled)
values ('Ballamy', '5ec7et', 25, 1,  0, 0, 0, 0);


insert into role(name, user_id)
values ('SUPER_ADMIN', 1);

insert into role(name, user_id)
values ('ADMIN', 2);

insert into role(name, user_id)
values ('USER', 3);


insert into role(name, user_id)
values ('USER', 4);

insert into user_type(name, user_id)
values ('Adult', 1);

insert into user_type(name, user_id)
values ('Adult', 2);

insert into user_type(name, user_id)
values ('Adult', 3);

insert into user_type(name, user_id)
values ('Adult', 4);

insert into event(name, description, organizer, `start`, `end`,image_urls)
values ('Open day', 'Event for new students', 1, '2023-07-16 10:00:00', '2023-07-16 13:00:00','["imagefile1","imagefile2","imagefile3"]');


insert into space_type(name)
values ('Building');

insert into space_type(name)
values('Room');

insert into space_type(name)
values ('Park');

insert into Space_type(name)
values ('Countryside trail');

insert into space(name, description, photo_urls, org_id, address_id, type_id)
values ('Abacws Building', 'Building for school of computer science and informatics', JSON_ARRAY(), 1, 1,  1);

insert into space(name, description, photo_urls, org_id, address_id,  type_id)
values ('Cardiff Student Union', 'Building for Cardiff University Student Union', JSON_ARRAY(), 1, 2,  1);

insert into space(name, description, photo_urls, org_id, address_id, type_id)
values ('Senghennydd Court', 'Cardiff University Hostel', JSON_ARRAY(), 1, 3,  1);


insert into subspace(name, description, photo_url, main_space,`type`)
values ('Room 3.65', 'Bookable room containing tables, chairs and white board','', 1,2);

insert into subspace(name, description, photo_url, main_space, `type` )
values ('Reception', 'Room for receptionist in student union building','', 2,2);

insert into subspace(name, description, photo_url, main_space, `type`)
values ('Reception', ' Room for receptionist in Senghennydd court','', 3,2);


insert into content(description, event_id)
values ('Content for open day event', 1);

insert into qrcode(space_id, sub_space_id, description, image_url,page_url, created_at)
values (1, 1, 'QR code for room 3.45 @ Abacws building', 'qr1.png', 'https://www.qrnavigation.com/content', '2023-05-16 10:00:00');

insert into qrcode(space_id, sub_space_id, description, image_url,page_url, created_at)
values (2, 2, 'QR code for reception @ Student Union building', 'qr2.png', 'https://www.qrnavigation.com/content', '2023-05-16 10:00:00');

insert into qrcode(space_id, sub_space_id, description, image_url,page_url, created_at)
values (3, 3, 'QR code for reception @ Senghennydd court', 'qr3.png', 'https://www.qrnavigation.com/content', '2023-05-16 10:00:00');

select u.*,r.name as ROLE from app_user u right outer join Role r on u.id=r.id;

