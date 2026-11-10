-- Run this in MySQL Workbench (connection: ContactBook) if the table does not exist yet.
CREATE DATABASE IF NOT EXISTS contactbook;
USE contactbook;

CREATE TABLE IF NOT EXISTS contacts (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    phone VARCHAR(15)  NOT NULL,
    email VARCHAR(100)
);

-- Optional demo data (run once, so the page is not empty during your demo)
INSERT INTO contacts (name, phone, email) VALUES
    ('Arun Kumar',   '9876543210', 'arun.kumar@example.com'),
    ('Divya Lakshmi','9123456780', 'divya.l@example.com'),
    ('Karthik Raja', '9988776655', NULL),
    ('Meena Sundar', '9090909090', 'meena.s@example.com');
