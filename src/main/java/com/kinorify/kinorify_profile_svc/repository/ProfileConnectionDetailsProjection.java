package com.kinorify.kinorify_profile_svc.repository;

import java.util.UUID;

public interface ProfileConnectionDetailsProjection {

    UUID getConnectionId();

    UUID getUserId();

    String getDisplayName();

    String getStatus();
}
