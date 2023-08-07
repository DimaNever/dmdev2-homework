CREATE DATABASE sto;

CREATE TABLE IF NOT EXISTS users
(
    id           BIGSERIAL PRIMARY KEY,
    role         VARCHAR(8)          NOT NULL,
    first_name   VARCHAR(128)        NOT NULL,
    last_name    VARCHAR(128)        NOT NULL,
    phone_number NUMERIC(11) UNIQUE  NOT NULL,
    address      VARCHAR(128)        NOT NULL,
    age          INT                 NOT NULL,
    email        VARCHAR(128) UNIQUE NOT NULL,
    password     VARCHAR(32) UNIQUE  NOT NULL
    );

CREATE TABLE IF NOT EXISTS client
(
    id           BIGSERIAL PRIMARY KEY,
    first_name   VARCHAR(128)        NOT NULL,
    last_name    VARCHAR(128)        NOT NULL,
    phone_number NUMERIC(11) UNIQUE  NOT NULL,
    email        VARCHAR(128) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS spare_part
(
    id          BIGSERIAL PRIMARY KEY,
    vendor_code VARCHAR(128) UNIQUE NOT NULL,
    title       VARCHAR(128)        NOT NULL,
    price       NUMERIC(11, 2) DEFAULT 0.00
    );

CREATE TABLE IF NOT EXISTS car
(
    id        BIGSERIAL PRIMARY KEY,
    model     VARCHAR(128) NOT NULL,
    year      NUMERIC(4) DEFAULT 1900,
    mileage   NUMERIC(7) DEFAULT 0,
    client_id INT REFERENCES client (id)
    );

CREATE TABLE IF NOT EXISTS order
(
    id             BIGSERIAL PRIMARY KEY,
    type_service   VARCHAR(128) NOT NULL,
    executors      INT REFERENCES users (id),
    car_id         INT REFERENCES car (id),
    start_date     DATE,
    end_date       DATE,
    spare_parts_id INT REFERENCES spare_part (id),
    price          NUMERIC(11, 2) DEFAULT 0.00
    );