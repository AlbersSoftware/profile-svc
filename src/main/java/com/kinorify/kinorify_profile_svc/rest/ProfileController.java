package com.kinorify.kinorify_profile_svc.rest;

import com.kinorify.kinorify_profile_svc.config.JwtService;
import com.kinorify.kinorify_profile_svc.dto.request.CreateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.request.UpdateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileResponseDTO;
import com.kinorify.kinorify_profile_svc.service.ProfileService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import com.kinorify.kinorify_profile_svc.entity.User;
import com.kinorify.kinorify_profile_svc.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger log =
            LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;

    private final JwtService jwtService;
    private final UserRepository userRepository;

@PostMapping
public ResponseEntity<ProfileResponseDTO> createProfile(
        @RequestBody CreateProfileRequestDTO request,
        @AuthenticationPrincipal Jwt jwt) {

    String cognitoSub =
            jwtService.getCognitoSub(jwt);

    User user =
            userRepository.findByCognitoSub(cognitoSub)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "User not found."));

    request.setUserId(
            user.getUserId());

    Optional<ProfileResponseDTO> existingProfile =
            profileService.getProfileByUserId(
                    user.getUserId());

    if (existingProfile.isPresent()) {

        log.warn(
                "Profile already exists for user {}.",
                user.getUserId());

        return ResponseEntity.ok(
                existingProfile.get());
    }

    ProfileResponseDTO createdProfile =
            profileService.createProfile(
                    request);

    log.info(
            "Successfully created profile for user {}.",
            user.getUserId());

    return ResponseEntity.ok(
            createdProfile);
}



    @PutMapping("/{userId}")
public ResponseEntity<ProfileResponseDTO> updateProfile(
        @PathVariable UUID userId,
        @RequestBody UpdateProfileRequestDTO request,
        @AuthenticationPrincipal Jwt jwt) {


    String cognitoSub =
            jwtService.getCognitoSub(jwt);


    UUID authenticatedUserId =
            userRepository
                    .findByCognitoSub(cognitoSub)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Authenticated user not found."))
                    .getUserId();


    if (!authenticatedUserId.equals(userId)) {

        throw new IllegalArgumentException(
                "Users may only update their own profile.");
    }


    log.info(
            "Updating profile for user {}.",
            userId);


    return ResponseEntity.ok(
            profileService.updateProfile(
                    userId,
                    request));
}



    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfileByUserId(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "Retrieving profile for user {}.",
                userId);


        return profileService.getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles(
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "Retrieving all profiles.");


        return ResponseEntity.ok(
                profileService.getAllProfiles());
    }

}
