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
