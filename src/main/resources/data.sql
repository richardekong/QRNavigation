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

insert into space(name, description, photo_urls, org_id, address_id, type)
values ('Abacws Building', 'Building for school of computer science and informatics', '[]', 1, 1,  1);

insert into space(name, description, photo_urls, org_id, address_id,  type)
values ('Cardiff Student Union', 'Building for Cardiff University Student Union', '[]', 1, 2,  1);

insert into space(name, description, photo_urls, org_id, address_id, type)
values ('Senghennydd Court', 'Cardiff University Hostel', '[]', 1, 3,  1);


insert into subspace(name, description, photo_url, main_space,`type`)
values ('Room 3.65', 'Bookable room containing tables, chairs and white board','', 1,2);

insert into subspace(name, description, photo_url, main_space,`type` )
values ('Reception', 'Room for receptionist in student union building','', 2,2);

insert into subspace(name, description, photo_url, main_space,`type`)
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


