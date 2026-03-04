drop database if exists AlbumTracker;
create database AlbumTracker;

create user if not exists 'manager'@'localhost' identified by 'password';
grant all privileges on AlbumTracker.* to 'manager'@'localhost';

flush privileges;