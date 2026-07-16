package com.kinorify.kinorify_profile_svc.service;

import com.kinorify.kinorify_profile_svc.entity.AccountStatus;

import com.kinorify.kinorify_profile_svc.dto.response.AccountStatusResponseDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountStatusService {

    Optional<AccountStatusResponseDTO> getAccountStatusById(
            short id);

    Optional<AccountStatusResponseDTO> getAccountStatusByUserId(
            UUID userId);

    List<AccountStatusResponseDTO> getAllAccountStatuses();

}
