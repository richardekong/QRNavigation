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
    latitude  decimal(10, 2),
    longitude decimal(10, 2)
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
    photo_urls    json               null,
    org_id        int default null,
    address_id    int default null,
    event_id      int default null,
    type_id       int default null,
    foreign key (org_id) references organization (id),
    foreign key (address_id) references address (id),
    foreign key (type_id) references space_type (id),
    foreign key (event_id) references event (id)
);

create table subspace
(
    id            int auto_increment primary key,
    `name`        varchar(30),
    `description` text,
    photo_url     text default null,
    main_space    int  default null,
    type_id       int  default null,
    event_id      int  default null,
    foreign key (main_space) references space (id),
    foreign key (event_id) references event (id),
    foreign key (type_id) references space_type (id)
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
