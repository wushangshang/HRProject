--DROP TABLE IF EXISTS user_account;
--
--CREATE TABLE "user_account"
--(
--    id VARCHAR(20) NOT NULL COMMENT '主键ID',
--    login VARCHAR(30) NOT NULL COMMENT 'login',
--    name VARCHAR(30) NOT NULL COMMENT '姓名',
--    salary INT(11) NOT NULL COMMENT 'salary',
--    start_date VARCHAR(50) NOT NULL COMMENT 'date',
--    PRIMARY KEY (id)
--);

--DROP TABLE IF EXISTS user;

create table account_user
(
id varchar(30) primary key not null,
login varchar(30) null,
name varchar(30) null,
salary float not null,
start_date date not null
);