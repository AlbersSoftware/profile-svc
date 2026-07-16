package com.kinorify.kinorify_profile_svc.dto.request;

import com.kinorify.kinorify_profile_svc.enums.ProfileConnectionStatusType;
import lombok.Data;

@Data
public class UpdateConnectionStatusRequestDTO {

    private ProfileConnectionStatusType status;

}
