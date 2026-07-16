package com.kinorify.kinorify_profile_svc.service;

import com.kinorify.kinorify_profile_svc.entity.User;

import com.kinorify.kinorify_profile_svc.dto.response.UserResponseDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(User user);

    Optional<UserResponseDTO> getUserById(
            UUID userId);

    Optional<UserResponseDTO> getUserByCognitoSub(
            String cognitoSub);

    Optional<UserResponseDTO> getUserByEmail(
            String email);

    List<UserResponseDTO> getAllUsers();

}
