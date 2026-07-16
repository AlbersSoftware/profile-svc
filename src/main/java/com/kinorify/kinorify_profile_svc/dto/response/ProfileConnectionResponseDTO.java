package com.kinorify.kinorify_profile_svc.dto.response;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ProfileConnectionResponseDTO {

    private UUID connectionId;

    private UUID requesterUserId;

    private UUID recipientUserId;

    private String status;

    private OffsetDateTime requestedAt;

    private OffsetDateTime acceptedAt;

    private OffsetDateTime updatedAt;

}
