CREATE TABLE customers(
    id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

SELECT * FROM customers;

ALTER TABLE customers ADD COLUMN primary_email VARCHAR(150);
