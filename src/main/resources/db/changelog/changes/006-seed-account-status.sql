--liquibase formatted sql

--changeset calbers:006

INSERT INTO profile.account_status (id, name)
VALUES
(1, 'ACTIVE'),
(2, 'DISABLED'),
(3, 'DELETED');
