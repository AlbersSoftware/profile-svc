package com.kinorify.kinorify_profile_svc.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ProfileResponseDTO {

    private UUID userId;

    private String displayName;

    private String phone;

    private String email;

    private UUID avatarMediaId;

    private String timezone;

    private LocalDate birthday;

    private String bio;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
