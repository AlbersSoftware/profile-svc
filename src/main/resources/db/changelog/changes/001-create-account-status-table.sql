--liquibase formatted sql

--changeset calbers:001

CREATE TABLE profile.account_status
(
    id SMALLINT PRIMARY KEY,

    name VARCHAR(30) NOT NULL UNIQUE
);
