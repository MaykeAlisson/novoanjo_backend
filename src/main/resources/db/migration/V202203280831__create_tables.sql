CREATE TABLE profile (
id BIGINT NOT NULL AUTO_INCREMENT,
name CHAR(1) NOT NULL,

  primary key (id)
) engine=innodb default charset=utf8;

CREATE TABLE service (
id BIGINT NOT NULL AUTO_INCREMENT,
description VARCHAR(100) NOT NULL,

  primary key (id)
) engine=innodb default charset=utf8;

CREATE TABLE address (
id BIGINT NOT NULL AUTO_INCREMENT,
zip_code VARCHAR(12) NOT NULL,
logradouro VARCHAR(130) NOT NULL,
complement VARCHAR(50),
number BIGINT NOT NULL
state VARCHAR(2) NOT NULL,

  primary key (id)
) engine=innodb default charset=utf8;


CREATE TABLE phone (
id BIGINT NOT NULL AUTO_INCREMENT,
ddd CHAR(3) NOT NULL,
number VARCHAR(10) NOT NULL,

  primary key (id)
) engine=innodb default charset=utf8;

CREATE TABLE user (
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
birth TIMESTAMP NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(200) NOT NULL,
data_cadastro TIMESTAMP NOT NULL,
profile_id BIGINT NOT NULL,
address_id BIGINT,
phone_id BIGINT,

  primary key (id)
) engine=innodb default charset=utf8;

ALTER TABLE user ADD CONSTRAINT profile_id_fk FOREIGN KEY (profile_id) REFERENCES profile (id);
ALTER TABLE address ADD CONSTRAINT address_id_fk FOREIGN KEY (address_id) REFERENCES address (id);
ALTER TABLE phone ADD CONSTRAINT phone_id_fk FOREIGN KEY (phone_id) REFERENCES phone (id);

create table user_service (
	user_id BIGINT NOT NULL,
	service_id BIGINT NOT NULL,

	primary key (user_id, service_id)
) engine=InnoDB default charset=utf8;

CREATE TABLE event (
id BIGINT NOT NULL AUTO_INCREMENT,
name VARCHAR(100) NOT NULL,
description VARCHAR(250) NOT NULL,
data TIMESTAMP NOT NULL,
data_cadastro TIMESTAMP NOT NULL,
approved CHAR(1) NOT NULL,
user_id BIGINT NOT NULL,
address_id BIGINT,

  primary key (id)
) engine=innodb default charset=utf8;


ALTER TABLE user ADD CONSTRAINT user_id_fk FOREIGN KEY (uuser_id) REFERENCES user (id);
ALTER TABLE address ADD CONSTRAINT address_id_fk FOREIGN KEY (address_id) REFERENCES address (id);
