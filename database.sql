CREATE TABLE customers(
    id varchar(255) not null primary key,
    name VARCHAR(100) NOT NULL
);

ALTER TABLE customers ADD COLUMN primary_email VARCHAR(150);
ALTER TABLE customers
    ALTER COLUMN id type uuid using id::uuid;

alter table customers
    alter column id set default uuid_generate_v4();

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE categories(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    description varchar(500)
);

ALTER TABLE customers
    ADD COLUMN age SMALLINT;
ALTER TABLE customers
    ADD COLUMN married BOOLEAN;

ALTER TABLE customers
    ADD COLUMN type VARCHAR(50);

ALTER TABLE categories
    ADD COLUMN created_at TIMESTAMP;
ALTER TABLE categories
    ADD COLUMN updated_at TIMESTAMP;

CREATE TABLE images(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    image BIGINT
);

CREATE TABLE company_files (
     id        SERIAL PRIMARY KEY,
     mime_type CHARACTER VARYING(255) NOT NULL,
     file_name CHARACTER VARYING(255) NOT NULL,
     file_data BYTEA NOT NULL,
);

CREATE TABLE members (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    email CHARACTER VARYING(150) NOT NULL,
    title CHARACTER VARYING(100),
    first_name CHARACTER VARYING(100),
    middle_name CHARACTER VARYING(100),
    last_name CHARACTER VARYING(100)
);

CREATE TABLE departments (
    company_id CHARACTER VARYING(100) NOT NULL,
    department_id CHARACTER VARYING(100) NOT NULL,
    name CHARACTER VARYING(150) NOT NULL,
    PRIMARY KEY(company_id, department_id)
);

CREATE TABLE hobbies (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    member_id INT NOT NULL,
    name CHARACTER VARYING(100) NOT NULL,
    CONSTRAINT fk_members_hobbies
        FOREIGN KEY (member_id)
            REFERENCES members(id)
);

CREATE TABLE skills (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
    member_id INT NOT NULL,
    name CHARACTER VARYING(100) NOT NULL,
    value INT NOT NULL,
    CONSTRAINT fk_members_skills
        FOREIGN KEY (member_id)
            REFERENCES members(id),
    CONSTRAINT skills_unique UNIQUE(member_id, name)
);

CREATE TABLE credentials(
    id CHARACTER VARYING(100) NOT NULL PRIMARY KEY,
    email CHARACTER VARYING(150) NOT NULL,
    password CHARACTER VARYING(150) NOT NULL
);

CREATE TABLE users(
    id CHARACTER VARYING(100) NOT NULL PRIMARY KEY,
    name CHARACTER VARYING(150) NOT NULL
);

CREATE TABLE wallet (
    id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id CHARACTER VARYING(100) NOT NULL,
    balance BIGINT NOT NULL,
    CONSTRAINT fk_users_wallet FOREIGN KEY (user_id) REFERENCES users(id)
);