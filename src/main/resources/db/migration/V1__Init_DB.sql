create sequence hibernate_sequence start 1 increment 1;

create table collection
(
    id            int8         not null,
    creation_date timestamp,
    description   varchar(1000),
    pic           varchar(255),
    title         varchar(255),
    theme_id      int8         not null,
    user_id       varchar(255) not null,
    primary key (id)
);

create table collection_field
(
    collection_id int8 not null,
    field_id      int8 not null,
    primary key (collection_id, field_id)
);

create table comment
(
    id         int8         not null,
    text       varchar(255),
    user_id    varchar(255) not null,
    message_id int8,
    primary key (id)
);

create table field
(
    id                     int8    not null,
    is_mark_down_supported boolean not null,
    text                   varchar(255),
    primary key (id)
);

create table item
(
    id            int8 not null,
    creation_time timestamp,
    title         varchar(255),
    collection_id int8 not null,
    primary key (id)
);

create table item_liker
(
    item_id  int8         not null,
    liker_id varchar(255) not null,
    primary key (item_id, liker_id)
);

create table item_tag
(
    item_id int8 not null,
    tag_id  int8 not null,
    primary key (item_id, tag_id)
);

create table tag
(
    id   int8 not null,
    name varchar(255),
    primary key (id)
);

create table theme
(
    id   int8 not null,
    text varchar(255),
    primary key (id)
);

create table usr
(
    id         varchar(255) not null,
    email      varchar(255),
    last_visit timestamp,
    name       varchar(255),
    non_locked boolean      not null,
    password   varchar(255),
    userpic    varchar(255),
    primary key (id)
);

create table usr_role
(
    usr_id varchar(255) not null,
    roles  varchar(255)
);

create table value
(
    id       int8 not null,
    value    varchar(255),
    field_id int8 not null,
    item_id  int8 not null,
    primary key (id)
);

alter table if exists collection
    add constraint FKe2m8l9u60uj5tpkihu26x66uf foreign key (theme_id) references theme;
alter table if exists collection
    add constraint FKiynwd9o1q6r02j468hcec94ou foreign key (user_id) references usr;
alter table if exists collection_field
    add constraint FKnnb7rea2hh50a2tbhyo90cmqq foreign key (field_id) references field;
alter table if exists collection_field
    add constraint FKmacpknqqpdx3pevlc6gmm63by foreign key (collection_id) references collection;
alter table if exists comment
    add constraint FKgcgdcgly6u49hf4g8y2di3g4p foreign key (user_id) references usr;
alter table if exists comment
    add constraint FKenk0kbmtyglbocqhc6gd1txx1 foreign key (message_id) references item;
alter table if exists item
    add constraint FKc5i55aa8x5qu6rmo4fkqtd07x foreign key (collection_id) references collection;
alter table if exists item_liker
    add constraint FKc34jehri7v21ulboj0irnxfm8 foreign key (liker_id) references usr;
alter table if exists item_liker
    add constraint FKh1dc7m1nh6fhr9a6gcxkxvpsr foreign key (item_id) references item;
alter table if exists item_tag
    add constraint FKde89ewingaktwyec3nh82pirt foreign key (tag_id) references tag;
alter table if exists item_tag
    add constraint FKjjb157o07631yt4a1h2fi2i4s foreign key (item_id) references item;
alter table if exists usr_role
    add constraint FK9ffk6ts9njcytrt8ft17fvr3p foreign key (usr_id) references usr;
alter table if exists value
    add constraint FK17fqpqgyppww4t5hbfa6e31gw foreign key (field_id) references field;
alter table if exists value
    add constraint FKr218fs6t2goa4renmm37kpdrk foreign key (item_id) references item;