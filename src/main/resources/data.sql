INSERT INTO TBL_CUSTOMER(first_name, last_name, email)
VALUES ('Lokesh', 'Gupta', 'abc@gmail.com'),
       ('Deja', 'Vu', 'xyz@email.com'),
       ('Caption', 'America', 'cap@marvel.com');

INSERT INTO TBL_CONTACT(customer_id, phone_number, address)
VALUES ('1', '+356 99720375', '445 Gzira'),
       ('2', '+356 99810425', '443 Rue Triq Dargens'),
       ('3', '+356 994213312', '95 Sliema');

INSERT INTO TBL_PRODUCT(product_type, product_package)
VALUES ('Internet', '250 Mbps'),
       ('Internet', '1 Gbps'),
       ('TV', '90 Channels'),
       ('TV', '140 Channels'),
       ('Telephony', 'Free on-net calls'),
       ('Telephony', 'Unlimited calls'),
       ('Mobile', 'Prepaid'),
       ('Mobile', 'Postpaid');

INSERT INTO TBL_ORDER(product_id, contact_id)
VALUES ('1', '1'),
       ('2', '2')
