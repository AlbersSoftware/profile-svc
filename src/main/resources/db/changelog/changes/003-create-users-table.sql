--liquibase formatted sql

--changeset calbers:003

CREATE TABLE profile.users
(
    user_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    cognito_sub VARCHAR(128) NOT NULL UNIQUE,

    email VARCHAR(255) NOT NULL UNIQUE,

    email_verified BOOLEAN NOT NULL DEFAULT FALSE,

    account_status_id SMALLINT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_users_account_status
        FOREIGN KEY (account_status_id)
        REFERENCES profile.account_status(id)
);
