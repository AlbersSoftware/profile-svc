package com.kinorify.kinorify_profile_svc.service.impl;

import com.kinorify.kinorify_profile_svc.dto.response.AccountStatusResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.AccountStatus;
import com.kinorify.kinorify_profile_svc.repository.AccountStatusRepository;
import com.kinorify.kinorify_profile_svc.service.AccountStatusService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountStatusServiceImpl
        implements AccountStatusService {

    private final AccountStatusRepository accountStatusRepository;


    @Override
    @Transactional(readOnly = true)
    public Optional<AccountStatusResponseDTO> getAccountStatusById(
            short id) {

        return accountStatusRepository.findById(id)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AccountStatusResponseDTO> getAccountStatusByUserId(
            UUID userId) {

        return accountStatusRepository.findStatusByUserId(userId)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public List<AccountStatusResponseDTO> getAllAccountStatuses() {

        return accountStatusRepository.findAllStatuses()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private AccountStatusResponseDTO mapToResponse(
            AccountStatus accountStatus) {

        AccountStatusResponseDTO dto =
                new AccountStatusResponseDTO();

        dto.setId(
                accountStatus.getId());

        dto.setName(
                accountStatus.getName());

        return dto;
    }
}
