package com.kinorify.kinorify_profile_svc.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ProfileConnectionOutgoingDetailsResponseDTO(
        UUID connectionId,
        UUID recipientUserId,
        String displayName,
        String email,
        String status,
        OffsetDateTime requestedAt
) {}
