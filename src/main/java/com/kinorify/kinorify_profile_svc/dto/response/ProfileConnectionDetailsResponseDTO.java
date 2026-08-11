package com.kinorify.kinorify_profile_svc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProfileConnectionDetailsResponseDTO {

    private UUID connectionId;

    private UUID userId;

    private String displayName;

    private String status;
}
