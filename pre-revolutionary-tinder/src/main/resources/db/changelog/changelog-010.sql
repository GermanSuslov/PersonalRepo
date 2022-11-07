--Создаем схему и назначаем владельца
create schema tinder authorization admin;

--Создаем таблицу
create table tinder.tinder_users (
  user_id       bigint as identity primary key,
  sex           varchar,
  name          varchar,
  story         text,
  looking_for   varchar
);

create table tinder.user_matches (
  matches_id    bigint generated by default as identity primary key,
  user_id       bigint,
  liked_id      bigint
);