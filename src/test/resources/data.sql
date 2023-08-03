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

