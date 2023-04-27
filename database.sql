create table customers(
    id varchar(255) not null primary key,
    name VARCHAR(100) NOT NULL
);

SELECT * FROM customers;

ALTER TABLE customers ADD COLUMN primary_email VARCHAR(150);
alter table customers
    alter column id type uuid using id::uuid;

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