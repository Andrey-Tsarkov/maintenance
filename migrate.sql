-- Сперва создаем БД main, которая указана в конфигах, далее создаем таблицы

-- "Справочник регламентных работ"
create table action
(
    id   serial constraint action_pk primary key,
    name varchar(255) not null,
    sort integer default 0
);
create unique index action_id_uindex on action (id);

-- "Проведенные работы"
create table event
(
    id         serial constraint event_pk primary key,
    action_id  integer           not null,
    mileage    integer default 0 not null,
    event_date date              not null
);
create unique index event_id_uindex on event (id);

-- Авторизация
-- "Роли пользователей"
create table auth_role
(
    id       bigserial constraint t_role_pk primary key,
    name varchar(255)                                      not null
);
create unique index t_role_id_uindex on auth_role (id);
INSERT INTO auth_role (id, name) VALUES (3, 'ROLE_USER'), (4, 'ROLE_ADMIN');

-- "Пользователи"
create table auth_user
(
    id       bigserial constraint t_user_pk primary key,
    username varchar(255),
    password varchar(255)
);
create unique index auth_user_username_uindex on auth_user (username);
-- создаем польователя admin с паролем admin
INSERT INTO auth_user (id, username, password) VALUES (1, 'admin', '$2a$10$/CtsD07S8DlJi/sWfA5.FO.Dyecf1m3rR4/D/ALO3eUY2w6SzLu46');

-- "Связка пользователи-роли"
create table auth_user_auth_role
(
    id       bigserial constraint t_user_roles_pk primary key,
    userentity_id bigint                                                  not null,
    roles_id      bigint                                                  not null
);
create unique index t_user_roles_id_uindex on auth_user_auth_role (id);
INSERT INTO auth_user_auth_role (id, userentity_id, roles_id) VALUES (1, 1, 1);