package com.kinorify.kinorify_profile_svc.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserResponseDTO {

    private UUID userId;

    private String cognitoSub;

    private String email;

    private boolean emailVerified;

    private String accountStatus;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
