DROP SCHEMA IF EXISTS `online-shopping-schema`;

CREATE SCHEMA `online-shopping-schema`;

use `online-shopping-schema`;

SET FOREIGN_KEY_CHECKS = 0;

create table `products`
(
  `code`        VARCHAR(20) not null,
  `image`       longblob,
  `name`        VARCHAR(255) not null,
  `price`       double precision not null,
  `create_date` datetime not null
) ;
 
alter table `products`
  add primary key (CODE) ;
---------------------------------------
-- Create table
create table `orders`
(
  `id`               VARCHAR(50) not null,
  `amount`           double precision not null,
  `customer_address` VARCHAR(255) not null,
  `customer_email`   VARCHAR(128) not null,
  `customer_name`    VARCHAR(255) not null,
  `customer_phone`   VARCHAR(128) not null,
  `order_date`       datetime not null,
  `order_num`        INTEGER not null
) ;
alter table `orders`
  add primary key (`id`) ;
alter table `orders`
  add constraint `ORDER_UK` unique (`order_num`) ;
---------------------------------------
 
-- Create table
create table `order_details`
(
  `id`         VARCHAR(50) not null,
  `amount`      double precision not null,
  `price`      double precision not null,
  `quanity`    INTEGER not null,
  `order_id`   VARCHAR(50) not null,
  `product_id` VARCHAR(20) not null
) ;
--  
alter table `order_details`
  add primary key (`id`) ;
alter table `order_details`
  add constraint `ORDER_DETAIL_ORD_FK` foreign key (`order_id`)
  references `orders` (`id`);
alter table `order_details`
  add constraint `ORDER_DETAIL_PROD_FK` foreign key (`product_id`)
  references `products` (`code`);
 
----------------
insert into `products` (`code`, `name`, `price`, `create_date`)
values ('S001', 'Margherita', 22, SYSDATE());
 
insert into `products` (`code`, `name`, `price`, `create_date`)
values ('S002', 'American Hot', 30, SYSDATE());
 
insert into `products` (`code`, `name`, `price`, `create_date`)
values ('S003', 'Prosciutto', 25, SYSDATE());
 
insert into `products` (`code`, `name`, `price`, `create_date`)
values ('S004', 'Hawaii', 27, SYSDATE());
 
insert into `products` (`code`, `name`, `price`, `create_date`)
values ('S005', 'Peperoni', 25, SYSDATE());