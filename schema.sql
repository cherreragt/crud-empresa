use db;

CREATE TABLE empresa (
    id int auto_increment,
    name varchar(80) not null ,
    issuer varchar(32) not null unique,
    create_at timestamp default current_timestamp,
    address varchar(150),

    primary key (id)
);