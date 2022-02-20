DROP TABLE IF EXISTS TBL_CUSTOMER;

CREATE TABLE TBL_CUSTOMER
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    email      VARCHAR(250) DEFAULT NULL
);

DROP TABLE IF EXISTS TBL_CONTACT;

CREATE TABLE TBL_CONTACT
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    customerId   INT          NOT NULL,
    phone_number VARCHAR(250) NOT NULL,
    address      VARCHAR(250) NOT NULL
);