INSERT INTO "users"(id, first_name, last_name, phone_number, email, status, reference, price_reduction, price_paid,
                    comment, created_date, modified_date,user_role, deleted)
VALUES (1, 'Florian', 'Dodaj', '069787878788', 'florian@gmail.com', 'INTERESTED', 'none', 3000, 270000, 'comment',
        '2022-09-01', '2022-09-01',1, false),
       (2, 'John', 'Smith', '0684778964', 'john@gmail.com', 'PAID', 'school', 3000, 22000, 'random stuff here',
        '2021-09-01', '2022-10-01', 2,false),
       (3, 'FirstName', 'LastName', '0690384654654', 'email@gmail.com', 'REGISTERED', 'reference', 0, 25000, 'comment',
        '2020-05-10', '2022-11-15', 2,false)
;