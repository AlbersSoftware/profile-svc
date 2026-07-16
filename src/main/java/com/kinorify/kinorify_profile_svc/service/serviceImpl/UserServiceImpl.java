package com.kinorify.kinorify_profile_svc.service.impl;

import com.kinorify.kinorify_profile_svc.dto.response.UserResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.User;
import com.kinorify.kinorify_profile_svc.repository.UserRepository;
import com.kinorify.kinorify_profile_svc.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;


    @Override
    @Transactional
    public UserResponseDTO createUser(User user) {

        return mapToResponse(
                userRepository.save(user));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> getUserById(
            UUID userId) {

        return userRepository.findUserById(userId)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> getUserByCognitoSub(
            String cognitoSub) {

        return userRepository.findByCognitoSub(cognitoSub)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserResponseDTO> getUserByEmail(
            String email) {

        return userRepository.findByEmail(email)
                .map(this::mapToResponse);
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAllUsers()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private UserResponseDTO mapToResponse(User user) {

    UserResponseDTO dto =
            new UserResponseDTO();

    dto.setUserId(
            user.getUserId());

    dto.setCognitoSub(
            user.getCognitoSub());

    dto.setEmail(
            user.getEmail());

    dto.setEmailVerified(
            Boolean.TRUE.equals(
                    user.getEmailVerified()));

    if (user.getAccountStatus() != null) {
        dto.setAccountStatus(
                user.getAccountStatus().getName());
    }

    dto.setCreatedAt(
            user.getCreatedAt());

    dto.setUpdatedAt(
            user.getUpdatedAt());

    return dto;
}

}
