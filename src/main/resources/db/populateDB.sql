DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2021-05-01 10:00:00', 'завтрак', 500, 100000),
       ('2021-05-01 12:00:00', 'обед', 600, 100000),
       ('2021-05-03 17:00:00', 'ужин', 700, 100000),
       ('2021-05-01 10:00:00', 'завтрак Админа', 1300, 100001),
       ('2021-05-01 12:00:00', 'обед Админа', 400, 100001),
       ('2021-05-01 17:00:00', 'ужин Админа', 500, 100001);
