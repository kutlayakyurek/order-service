DROP TABLE IF EXISTS TBL_ORDER;
DROP TABLE IF EXISTS TBL_PRODUCT;
DROP TABLE IF EXISTS TBL_CONTACT;
DROP TABLE IF EXISTS TBL_CUSTOMER;

CREATE TABLE TBL_CUSTOMER
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    email      VARCHAR(250) DEFAULT NULL
);

CREATE TABLE TBL_CONTACT
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    customer_id  INT          NOT NULL,
    phone_number VARCHAR(250) NOT NULL,
    address      VARCHAR(250) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES TBL_CUSTOMER (id)
);

CREATE TABLE TBL_PRODUCT
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    product_type    VARCHAR(250) NOT NULL,
    product_package VARCHAR(250) NOT NULL
);

CREATE TABLE TBL_ORDER
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    contact_id INT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES TBL_PRODUCT (id),
    FOREIGN KEY (contact_id) REFERENCES TBL_CONTACT (id)
);