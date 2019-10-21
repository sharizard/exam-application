drop table if exists device;
drop table if exists hibernate_sequence;
drop table if exists user;

create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
create table device (id bigint not null, created_at datetime, updated_at datetime, name varchar(255), owner varchar(255), primary key (id));
create table measurement (id bigint not null, created_at datetime, updated_at datetime, device_id bigint, latitude bigint, longitude bigint, reading_time datetime, sievert varchar(255), primary key (id));
