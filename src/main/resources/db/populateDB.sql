DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;
ALTER SEQUENCE meals_seq RESTART WITH 100;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (id, date_time, description, calories, user_id)
VALUES (100, '2021-05-01 10:00:00', 'завтрак', 500, 100000),
       (101, '2021-05-01 12:00:00', 'обед', 600, 100000),
       (102, '2021-05-01 17:00:00', 'ужин', 700, 100000),
       (103, '2021-05-01 10:00:00', 'завтрак', 1300, 100001),
       (104, '2021-05-01 12:00:00', 'обед', 400, 100001),
       (105, '2021-05-01 17:00:00', 'ужин', 500, 100001);
