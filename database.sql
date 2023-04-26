create table customers(
    id varchar(255) not null primary key,
    name VARCHAR(100) NOT NULL
);

SELECT * FROM customers;

ALTER TABLE customers ADD COLUMN primary_email VARCHAR(150);

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE categories(
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY NOT NULL,
    name VARCHAR(100) NOT NULL,
    description varchar(500)
);