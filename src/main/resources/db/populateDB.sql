DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2022-02-02 15:43:00.000', 'Завтрак', 1337, 100000),
        ('2022-02-02 15:41:00.000', 'Ужин', 1337, 100000),
        ('2020-12-21 09:43:00.000', 'Обед', 1337, 100001),
        ('2022-01-16 20:00:00.000', 'описание', 1337, 100000),
        ('2021-03-14 15:41:00.000', 'Ужин', 1337, 100001),
        ('2022-01-13 13:00:00.000', 'Еда на граничное значение', 1337, 100000),
        ('2022-01-09 05:00:00.000', 'Завтрак', 1337, 100000),
        ('2020-09-25 18:12:00.000', 'Завтрак', 1337, 100001),
        ('2021-12-28 10:57:00.000', 'Обед', 1337, 100000),
        ('2021-12-09 13:34:00.000', 'Ужин', 1337, 100000);
