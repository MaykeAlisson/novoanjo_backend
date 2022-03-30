CREATE TABLE profile (
id BIGSERIAL PRIMARY KEY,
name CHAR(1) NOT NULL
);

CREATE TABLE service (
id BIGSERIAL PRIMARY KEY,
description VARCHAR(100) NOT NULL
);

CREATE TABLE address (
id BIGSERIAL PRIMARY KEY,
zip_code VARCHAR(12) NOT NULL,
logradouro VARCHAR(130) NOT NULL,
complement VARCHAR(50),
number BIGINT NOT NULL,
state VARCHAR(2) NOT NULL
);


CREATE TABLE phone (
id BIGSERIAL PRIMARY KEY,
ddd CHAR(3) NOT NULL,
number VARCHAR(10) NOT NULL
);

CREATE TABLE usuario(
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
birth TIMESTAMP NOT NULL,
email VARCHAR(100) unique  NOT NULL,
password VARCHAR(200) NOT NULL,
data_cadastro TIMESTAMP NOT NULL,
profile_id BIGINT NOT NULL,
address_id BIGINT,
phone_id BIGINT
);

ALTER TABLE usuario ADD CONSTRAINT profile_id_fk FOREIGN KEY (profile_id) REFERENCES profile(id);
ALTER TABLE usuario ADD CONSTRAINT address_id_fk FOREIGN KEY (address_id) REFERENCES address(id);
ALTER TABLE usuario ADD CONSTRAINT phone_id_fk FOREIGN KEY (phone_id) REFERENCES phone (id);

create table user_service (
	user_id BIGINT NOT NULL,
	service_id BIGINT NOT NULL,

	primary key (user_id, service_id)
);

CREATE TABLE event (
id BIGSERIAL PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description VARCHAR(250) NOT NULL,
data TIMESTAMP NOT NULL,
data_cadastro TIMESTAMP NOT NULL,
approved CHAR(1) NOT NULL,
user_id BIGINT NOT NULL,
address_id BIGINT
);


ALTER TABLE event ADD CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES usuario (id);
ALTER TABLE event ADD CONSTRAINT address_id_fk FOREIGN KEY (address_id) REFERENCES address (id);
