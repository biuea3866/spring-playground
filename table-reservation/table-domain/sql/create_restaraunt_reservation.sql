create table reservation (
                             id bigint not null auto_increment primary key,
                             customer_id bigint not null,
                             restaurant_id bigint not null,
                             entrance bit not null,
                             start datetime not null,
                             end datetime not null,
                             a_party_of int not null,
                             status varchar(50) not null,
                             reservationNumber int,
                             requested_at timestamp not null,
                             confirmed_at timestamp,
                             canceled_at timestamp,
                             declined_at timestamp,
                             created_at timestamp not null default current_timestamp,
                             updated_at timestamp not null default current_timestamp on update current_timestamp,
                             deleted_at timestamp
) engine=InnoDB default charset=utf8mb4;

create index customer_id on reservation (customer_id);
create index restaurant_id on reservation (restaurant_id);

create table restaurant(
                           id bigint not null auto_increment primary key,
                           name varchar(255) not null,
                           address varchar(255),
                           province varchar(255),
                           municipality varchar(255),
                           city_district varchar(255),
                           lot_number varchar(255),
                           registration_number varchar(30),
                           s3_key varchar(255),
                           s3_bucket varchar(255),
                           filename varchar(255),
                           opening_time timestamp not null,
                           closing_time timestamp not null,
                           start_break_time timestamp,
                           end_break_time timestamp,
                           closed_days varchar(255),
                           contact varchar(30),
                           description text,
                           status varchar(50) not null,
                           opened_at timestamp,
                           closed_at timestamp,
                           created_at timestamp not null default current_timestamp,
                           updated_at timestamp not null default current_timestamp on update current_timestamp,
                           deleted_at timestamp
) engine=InnoDB default charset=utf8mb4;

create table restaurant_reservation_relation(
                                                id bigint not null auto_increment primary key,
                                                restaurant_id bigint not null,
                                                reservation_id bigint not null,
                                                created_at timestamp not null default current_timestamp,
                                                updated_at timestamp not null default current_timestamp on update current_timestamp,
                                                deleted_at timestamp
) engine=InnoDB default charset=utf8mb4;

create index restaurant_id on restaurant_reservation_relation (restaurant_id);
create index reservation_id on restaurant_reservation_relation (reservation_id);

create table `table`(
                        id bigint not null auto_increment primary key,
                        restaurant_id bigint not null,
                        available bit not null,
                        table_number int not null,
                        status varchar(50) not null,
                        created_at timestamp not null default current_timestamp,
                        updated_at timestamp not null default current_timestamp on update current_timestamp,
                        deleted_at timestamp
) engine = InnoDB default charset=utf8mb4;

create index restaurant_id on `table` (restaurant_id);

create table restaurant_management(
                                      id bigint not null auto_increment primary key,
                                      restaurant_id bigint not null,
                                      openTime timestamp not null,
                                      auto_confirm bit not null,
                                      created_at timestamp not null default current_timestamp,
                                      updated_at timestamp not null default current_timestamp on update current_timestamp,
                                      deleted_at timestamp
) engine = InnoDB default charset=utf8mb4;

create index restaurant_id on restaurant_management (restaurant_id);