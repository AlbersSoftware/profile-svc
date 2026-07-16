--liquibase formatted sql

--changeset calbers:007

INSERT INTO profile.profile_connection_status (id, name)
VALUES
(1, 'PENDING'),
(2, 'ACCEPTED'),
(3, 'DECLINED'),
(4, 'BLOCKED');
