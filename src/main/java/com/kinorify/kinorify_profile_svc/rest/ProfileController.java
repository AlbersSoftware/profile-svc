package com.kinorify.kinorify_profile_svc.rest;

import com.kinorify.kinorify_profile_svc.dto.request.CreateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.request.UpdateProfileRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileResponseDTO;
import com.kinorify.kinorify_profile_svc.service.ProfileService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;



    /**
     * Creates a profile for an existing user.
     *
     * TODO:
     * Once Cognito JWT authentication is implemented,
     * userId should come from the JWT instead of the request body.
     */
    @PostMapping
    public ResponseEntity<ProfileResponseDTO> createProfile(
            @RequestBody CreateProfileRequestDTO request) {


        return ResponseEntity.ok(
                profileService.createProfile(request));
    }



    @PutMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> updateProfile(
            @PathVariable UUID userId,
            @RequestBody UpdateProfileRequestDTO request) {


        return ResponseEntity.ok(
                profileService.updateProfile(
                        userId,
                        request));
    }



    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDTO> getProfileByUserId(
            @PathVariable UUID userId) {


        return profileService.getProfileByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping
    public ResponseEntity<List<ProfileResponseDTO>> getAllProfiles() {


        return ResponseEntity.ok(
                profileService.getAllProfiles());
    }

}
