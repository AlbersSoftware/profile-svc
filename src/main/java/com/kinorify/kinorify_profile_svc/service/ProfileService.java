package com.kinorify.kinorify_profile_svc.service;

import com.kinorify.kinorify_profile_svc.dto.request.CreateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.request.UpdateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface ProfileService {


    ProfileResponseDTO createProfile(
            CreateProfileRequestDTO request);



    ProfileResponseDTO updateProfile(
            UUID userId,
            UpdateProfileRequestDTO request);



    Optional<ProfileResponseDTO> getProfileByUserId(
            UUID userId);



    List<ProfileResponseDTO> getAllProfiles();

}
