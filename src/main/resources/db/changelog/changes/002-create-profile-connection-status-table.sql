--liquibase formatted sql

--changeset calbers:002

CREATE TABLE profile.profile_connection_status
(
    id SMALLINT PRIMARY KEY,

    name VARCHAR(30) NOT NULL UNIQUE
);
