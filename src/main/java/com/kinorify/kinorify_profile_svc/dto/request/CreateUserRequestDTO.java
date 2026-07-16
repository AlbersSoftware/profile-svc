package com.kinorify.kinorify_profile_svc.dto.request;

import lombok.Data;

@Data
public class CreateUserRequestDTO {

    private String cognitoSub;

    private String email;

    private boolean emailVerified;

}
