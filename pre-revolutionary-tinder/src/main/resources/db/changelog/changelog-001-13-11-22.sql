--Создаем схему и назначаем владельца
create schema if not exists tinder authorization admin;

--Создаем таблицу
create table if not exists tinder.tinder_users (
  user_id       bigserial primary key,
  sex           text,
  name          text,
  story         text,
  looking_for   text
);

create table if not exists tinder.user_matches (
  matches_id    bigserial primary key,
  user_id       bigserial,
  liked_id      bigserial
);