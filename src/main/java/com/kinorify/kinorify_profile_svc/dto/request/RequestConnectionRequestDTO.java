package com.kinorify.kinorify_profile_svc.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestConnectionRequestDTO {

    private UUID requesterUserId;

    private UUID recipientUserId;

}
