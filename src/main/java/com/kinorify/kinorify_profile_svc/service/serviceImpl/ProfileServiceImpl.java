package com.kinorify.kinorify_profile_svc.service.impl;

import com.kinorify.kinorify_profile_svc.dto.request.CreateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.request.UpdateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.Profile;
import com.kinorify.kinorify_profile_svc.entity.User;
import com.kinorify.kinorify_profile_svc.repository.ProfileRepository;
import com.kinorify.kinorify_profile_svc.repository.UserRepository;
import com.kinorify.kinorify_profile_svc.service.ProfileService;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileSearchResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProfileServiceImpl
        implements ProfileService {


    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;



    



@Override
@Transactional
public ProfileResponseDTO createProfile(
        CreateProfileRequestDTO request) {


    User user =
            userRepository.findById(
                    request.getUserId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "User not found."));


    if (
        profileRepository.existsByDisplayNameIgnoreCase(
                request.getDisplayName())
    ) {

        throw new IllegalArgumentException(
                "username is already taken");

    }


    Profile profile =
            new Profile();


    profile.setUser(
            user);


    profile.setDisplayName(
            request.getDisplayName());

    profile.setPhone(
            request.getPhone());

    profile.setEmail(
            request.getEmail());

    profile.setAvatarMediaId(
            request.getAvatarMediaId());

    profile.setTimezone(
            request.getTimezone());

    profile.setBirthday(
            request.getBirthday());

    profile.setBio(
            request.getBio());


    return mapToResponse(
            profileRepository.save(
                    profile));
}


@Override
@Transactional
public ProfileResponseDTO updateProfile(
        UUID userId,
        UpdateProfileRequestDTO request) {


    Profile profile =
            profileRepository.findByUserId(
                    userId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Profile not found."));


    if (
        profileRepository
                .existsByDisplayNameIgnoreCaseAndUserIdNot(
                        request.getDisplayName(),
                        userId)
    ) {

        throw new IllegalArgumentException(
                "username is already taken");

    }


    profile.setDisplayName(
            request.getDisplayName());

    profile.setPhone(
            request.getPhone());

    profile.setEmail(
            request.getEmail());

    profile.setAvatarMediaId(
            request.getAvatarMediaId());

    profile.setTimezone(
            request.getTimezone());

    profile.setBirthday(
            request.getBirthday());

    profile.setBio(
            request.getBio());


    return mapToResponse(
            profileRepository.save(
                    profile));
}







    @Override
    @Transactional(readOnly = true)
    public Optional<ProfileResponseDTO> getProfileByUserId(
            UUID userId) {


        return profileRepository.findByUserId(userId)
                .map(this::mapToResponse);
    }



    @Override
    @Transactional(readOnly = true)
    public List<ProfileResponseDTO> getAllProfiles() {


        return profileRepository.findAllProfiles()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


@Override
public Page<ProfileSearchResponseDTO> searchProfiles(String query, Pageable pageable) {

    Page<Profile> profiles;

    if (query == null || query.trim().length() < 3) {

        profiles = profileRepository.findAll(pageable);

    } else {

        profiles = profileRepository.searchProfiles(query.trim(), pageable);

    }


    return profiles.map(profile ->
            new ProfileSearchResponseDTO(
                    profile.getUserId(),
                    profile.getDisplayName(),
                    profile.getAvatarMediaId()
            )
    );

}



    private ProfileResponseDTO mapToResponse(
            Profile profile) {


        ProfileResponseDTO dto =
                new ProfileResponseDTO();


        dto.setUserId(
                profile.getUser().getUserId());


        dto.setDisplayName(
                profile.getDisplayName());


        dto.setPhone(
                profile.getPhone());


        dto.setEmail(
                profile.getEmail());


        dto.setAvatarMediaId(
                profile.getAvatarMediaId());


        dto.setTimezone(
                profile.getTimezone());


        dto.setBirthday(
                profile.getBirthday());


        dto.setBio(
                profile.getBio());


        dto.setCreatedAt(
                profile.getCreatedAt());


        dto.setUpdatedAt(
                profile.getUpdatedAt());


        return dto;
    }

}
