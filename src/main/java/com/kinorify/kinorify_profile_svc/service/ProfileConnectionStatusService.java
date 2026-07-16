package com.kinorify.kinorify_profile_svc.service;

import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionStatusResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProfileConnectionStatusService {

    Optional<ProfileConnectionStatusResponseDTO> getProfileConnectionStatusById(
            short id);

    List<ProfileConnectionStatusResponseDTO> getAllProfileConnectionStatuses();

}
