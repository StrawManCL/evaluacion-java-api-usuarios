create sequence telefono_seq start with 1 increment by 50;
create table telefono
(
    id           bigint not null,
    city_code    varchar(255),
    country_code varchar(255),
    number       varchar(255),
    usuario_id  uuid   not null,
    primary key (id)
);
create table usuario
(
    active       boolean not null,
    created      timestamp(6) with time zone,
    modified     timestamp(6) with time zone,
    last_login timestamp(6) with time zone,
    id           uuid    not null,
    password        varchar(255),
    email       varchar(255) unique,
    name       varchar(255),
    token        varchar(255),
    primary key (id)
);
