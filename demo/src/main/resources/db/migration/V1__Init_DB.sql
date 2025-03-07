create table if not exists users
(
    id           bigserial
        primary key,
    bio          text,
    created_date timestamp(6),
    email        varchar(255) unique,
    lastname     varchar(255) not null,
    name         varchar(255) not null,
    password     varchar,
    username     varchar(255) unique
);

create table post
(
    id           bigserial
        primary key,
    caption      varchar(255),
    created_date timestamp(6),
    likes        integer,
    location     varchar(255),
    title        varchar(255),
    user_id      bigint references users
);

create table comment
(
    id  bigserial primary key,
    created_date timestamp(6),
    message      text         not null,
    user_id      bigint       not null,
    username     varchar(255) not null,
    post_id      bigint references post
);

create table post_liked_users
(
    post_id     bigint not null references post,
    liked_users varchar(255)
);


create table user_role
(
    user_id bigint not null references users,
    roles   smallint
        constraint user_role_roles_check
            check ((roles >= 0) AND (roles <= 1))
);

create table image_model
(
    id          serial
        primary key,
    name        varchar not null,
    encoded_image text,
    user_id     bigint,
    post_id     bigint
);