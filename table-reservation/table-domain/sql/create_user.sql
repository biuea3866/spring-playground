create table user
(
    id           bigint       not null auto_increment primary key,
    email        varchar(50)  not null,
    password     varchar(255) not null,
    nickname     varchar(50)  not null,
    phone_number varchar(20),
    status       varchar(20)  not null,
    grade        varchar(20)  not null,
    s3_key       varchar(255),
    s3_bucket    varchar(255),
    filename     varchar(255),
    created_at   datetime     not null default current_timestamp,
    updated_at   datetime     not null default current_timestamp on update current_timestamp,
    deleted_at   datetime
) engine = InnoDB default charset = utf8mb4;