package com.kinorify.kinorify_profile_svc.service.impl;

import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionStatusResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.ProfileConnectionStatus;
import com.kinorify.kinorify_profile_svc.repository.ProfileConnectionStatusRepository;
import com.kinorify.kinorify_profile_svc.service.ProfileConnectionStatusService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileConnectionStatusServiceImpl
        implements ProfileConnectionStatusService {

    private final ProfileConnectionStatusRepository repository;


    @Override
    @Transactional(readOnly = true)
    public Optional<ProfileConnectionStatusResponseDTO> getProfileConnectionStatusById(
            short id) {

        return repository.findById(id)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProfileConnectionStatusResponseDTO> getAllProfileConnectionStatuses() {

        return repository.findAllStatuses()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private ProfileConnectionStatusResponseDTO mapToResponse(
            ProfileConnectionStatus status) {

        ProfileConnectionStatusResponseDTO dto =
                new ProfileConnectionStatusResponseDTO();

        dto.setId(status.getId());

        dto.setName(status.getName());

        return dto;
    }
}
