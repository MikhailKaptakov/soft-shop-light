DELETE FROM order_position;
DELETE FROM product;
DELETE FROM article;
DELETE FROM product_order_no_authority;
ALTER SEQUENCE order_position_seq RESTART WITH 1000;
ALTER SEQUENCE product_seq RESTART WITH 1000;
ALTER SEQUENCE article_seq RESTART WITH 1000;
ALTER SEQUENCE product_order_seq RESTART WITH 1000;

INSERT INTO product (id, name, vendor, country, license_time, description, price, delivery_time_in_days, nds_include, req_tech_support, available, favorite)
VALUES (1, 'prod1','vendor1','country1','33 minutes','description',1000,5,true,true, true, false),
       (2, 'prod2', 'vendor2', 'country2', '33 years' , 'undescription', 5, 55, true, false, true, true),
       (3, 'prod3', 'vendor3', 'country3', '5 seconds' , 'useless program', 100000, 5, false, true, true, false),
       (4, 'prod4', 'vendor3', 'country3', '5 seconds' , 'useless program', 1000, 5, false, false, true, false),
       (5, 'prod5', 'vendor3', 'country3', '5 seconds' , 'useless program', 10000, 5, false, true, false, true);

INSERT INTO article (id, header, preview, text, available)
VALUES (1, 'Header','Preview','Text',true),
       (2, 'Header2', 'Preview2', 'Text2', true),
       (3, 'Header3', 'Preview3', 'Text3', false);

INSERT INTO product_order_no_authority (id, email, telephone_number, firstname, surname, second_name, company_name, address, comment, order_date_time)
VALUES (1, 'prod@test.ru', '89001002020', 'Soul', 'Goodman' , 'Soulovich', '', '', '', current_timestamp),
       (2, 'prod2@test.ru', '89003002020', 'Soul2', 'Goodman2' , 'Soulovich2', '1', '1', '1', '2020-01-31 21:00:00.000');

INSERT INTO order_position (id, product_id, order_id, product_value)
VALUES  (1, 1, 1, 1),
        (2, 3, 1, 2),
        (3, 5, 1, 2),
        (4, 2, 2, 2),
        (5, 3, 2, 2);




