package com.kinorify.kinorify_profile_svc.dto.response;

import java.util.UUID;

public record ProfileSearchResponseDTO(

        UUID userId,

        String displayName,

        UUID avatarMediaId

) {}
