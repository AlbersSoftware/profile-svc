--liquibase formatted sql

--changeset calbers:005

CREATE TABLE profile.profile_connection
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    requester_user_id UUID NOT NULL,

    recipient_user_id UUID NOT NULL,

    profile_connection_status_id SMALLINT NOT NULL,

    requested_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    accepted_at TIMESTAMPTZ,

    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_profile_connection_requester
        FOREIGN KEY (requester_user_id)
        REFERENCES profile.users(user_id),

    CONSTRAINT fk_profile_connection_recipient
        FOREIGN KEY (recipient_user_id)
        REFERENCES profile.users(user_id),

    CONSTRAINT fk_profile_connection_status
        FOREIGN KEY (profile_connection_status_id)
        REFERENCES profile.profile_connection_status(id),

    CONSTRAINT uq_profile_connection
        UNIQUE(requester_user_id, recipient_user_id),

    CONSTRAINT chk_profile_connection_not_self
        CHECK (requester_user_id <> recipient_user_id)
);

CREATE INDEX idx_profile_connection_requester
    ON profile.profile_connection(requester_user_id);

CREATE INDEX idx_profile_connection_recipient
    ON profile.profile_connection(recipient_user_id);
