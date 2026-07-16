--liquibase formatted sql

--changeset calbers:008

INSERT INTO profile.profile_connection_status (id, name)
VALUES
(5, 'REMOVED');
