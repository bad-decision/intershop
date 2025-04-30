TRUNCATE payment_account CASCADE;

ALTER SEQUENCE payment_account_id_seq RESTART WITH 1;

INSERT INTO PAYMENT_ACCOUNT (username, balance)
VALUES
    ('user01', 1000),
    ('user02', 2000);
