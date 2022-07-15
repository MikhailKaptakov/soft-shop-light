DELETE FROM order_position;
DELETE FROM product;
DELETE FROM product_order_no_authority;

INSERT INTO product (id, name, vendor, country, license_time, description, price, delivery_time_in_days, nds_include, req_tech_support)
VALUES (1, "prod1", "vendor1", "country1", "33 minutes" , "description", 1000, 5, "true", "true"),
       (2, "prod2", "vendor2", "country2", "33 years" , "undescription", 5, 550, true, true),
       (3, "prod3", "vendor3", "country3", "5 seconds" , "useless program", 100000, 5, false, false),
       (4, "prod3", "vendor3", "country3", "5 seconds" , "useless program", 1000, 5, false, false),
       (5, "prod3", "vendor3", "country3", "5 seconds" , "useless program", 10000, 5, false, false);

INSERT INTO product_order_no_authority (id, email, telephone_number, firstname, surname, second_name, company_name, address, comment, order_date_time)
VALUES (1, "prod@test.ru", "89001002020", "Soul", "Goodman" , "Soulovich", "", "", "", NOW()),
       (2, "prod2@test.ru", "89003002020", "Soul2", "Goodman2" , "Soulovich2", "1", "1", "1", '2020-01-31 21:00:00');

INSERT INTO order_position (id, product_id, order_id, product_value)
VALUES  (1, 1, 1, 2),
        (2, 1, 1, 2),
        (3, 1, 1, 2),
        (4, 2, 2, 2),
        (5, 3, 2, 2);




