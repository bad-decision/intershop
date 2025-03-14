--liquibase formatted sql

--changeset azmeev:1
CREATE TABLE SHOP_ITEM (
    ID bigint not null primary key generated always as identity,
    TITLE varchar(256) not null,
    DESCRIPTION text,
    IMG_PATH varchar(256),
    PRICE double precision not null,
    CREATED_DATE timestamp,
    LAST_MODIFIED_DATE timestamp
);
--changeset azmeev:2
CREATE TABLE SHOP_CART_ITEM (
    ID bigint not null primary key generated always as identity,
    ITEM_ID bigint not null references SHOP_ITEM(ID),
    COUNT bigint,
    CREATED_DATE timestamp,
    LAST_MODIFIED_DATE timestamp
);
--changeset azmeev:3
CREATE TABLE SHOP_ORDER (
    ID bigint not null primary key generated always as identity,
    CREATED_DATE timestamp,
    LAST_MODIFIED_DATE timestamp
);
--changeset azmeev:4
CREATE TABLE SHOP_ORDER_ITEM (
    ID bigint not null primary key generated always as identity,
    ORDER_ID bigint not null references SHOP_ORDER(ID),
    TITLE varchar(256) not null,
    DESCRIPTION text,
    IMG_PATH varchar(256),
    COUNT bigint not null,
    PRICE double precision not null,
    CREATED_DATE timestamp,
    LAST_MODIFIED_DATE timestamp
);