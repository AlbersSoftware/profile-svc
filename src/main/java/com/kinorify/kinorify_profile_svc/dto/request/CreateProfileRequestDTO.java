package com.kinorify.kinorify_profile_svc.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateProfileRequestDTO {

    private UUID userId;

    private String displayName;

    private String phone;

    private String email;

    private UUID avatarMediaId;

    private String timezone;

    private LocalDate birthday;

    private String bio;

}
