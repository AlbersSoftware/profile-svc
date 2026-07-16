--liquibase formatted sql

--changeset calbers:004

CREATE TABLE profile.profile
(
    user_id UUID PRIMARY KEY,

    display_name VARCHAR(100) NOT NULL,

    phone VARCHAR(30),

    email VARCHAR(255),

    avatar_media_id UUID,

    timezone VARCHAR(100),

    birthday DATE,

    bio VARCHAR(500),

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_profile_user
        FOREIGN KEY (user_id)
        REFERENCES profile.users(user_id)
        ON DELETE CASCADE
);
