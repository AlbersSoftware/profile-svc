package com.kinorify.kinorify_profile_svc.rest;

import com.kinorify.kinorify_profile_svc.dto.request.RequestConnectionRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionResponseDTO;
import com.kinorify.kinorify_profile_svc.service.ProfileConnectionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/profile-connections")
@RequiredArgsConstructor
public class ProfileConnectionController {

    private final ProfileConnectionService profileConnectionService;


    /**
     * Sends a connection request.
     *
     * TODO:
     * Once JWT authentication is implemented,
     * requesterUserId should come from the JWT instead of the request body.
     */
    @PostMapping
    public ResponseEntity<ProfileConnectionResponseDTO> requestConnection(
            @RequestBody RequestConnectionRequestDTO request) {

        return ResponseEntity.ok(
                profileConnectionService.requestConnection(
                        request.getRequesterUserId(),
                        request.getRecipientUserId()));
    }


    @PutMapping("/{connectionId}/accept")
    public ResponseEntity<ProfileConnectionResponseDTO> acceptConnection(
            @PathVariable UUID connectionId) {

        return ResponseEntity.ok(
                profileConnectionService.acceptConnection(connectionId));
    }


    @PutMapping("/{connectionId}/decline")
    public ResponseEntity<ProfileConnectionResponseDTO> declineConnection(
            @PathVariable UUID connectionId) {

        return ResponseEntity.ok(
                profileConnectionService.declineConnection(connectionId));
    }


    @PutMapping("/{connectionId}/remove")
    public ResponseEntity<ProfileConnectionResponseDTO> removeConnection(
            @PathVariable UUID connectionId) {

        return ResponseEntity.ok(
                profileConnectionService.removeConnection(connectionId));
    }


    /**
     * Placeholder until blocking rules are finalized.
     */
    @PutMapping("/{connectionId}/block")
    public ResponseEntity<ProfileConnectionResponseDTO> blockConnection(
            @PathVariable UUID connectionId) {

        return ResponseEntity.ok(
                profileConnectionService.blockConnection(connectionId));
    }


    @GetMapping("/{connectionId}")
    public ResponseEntity<ProfileConnectionResponseDTO> getConnectionById(
            @PathVariable UUID connectionId) {

        return profileConnectionService
                .getConnectionById(connectionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/users")
    public ResponseEntity<ProfileConnectionResponseDTO> getConnectionByUsers(

            @RequestParam UUID requesterUserId,

            @RequestParam UUID recipientUserId) {

        return profileConnectionService
                .getConnectionByUsers(
                        requesterUserId,
                        recipientUserId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getConnectionsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                profileConnectionService
                        .getConnectionsByUserId(userId));
    }


    @GetMapping("/user/{userId}/incoming")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getIncomingRequestsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                profileConnectionService
                        .getIncomingRequestsByUserId(userId));
    }


    @GetMapping("/user/{userId}/outgoing")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getOutgoingRequestsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                profileConnectionService
                        .getOutgoingRequestsByUserId(userId));
    }


    @GetMapping("/user/{userId}/accepted")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getAcceptedConnectionsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                profileConnectionService
                        .getAcceptedConnectionsByUserId(userId));
    }


    @GetMapping("/user/{userId}/declined")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getDeclinedConnectionsByUserId(
            @PathVariable UUID userId) {

        return ResponseEntity.ok(
                profileConnectionService
                        .getDeclinedConnectionsByUserId(userId));
    }

}
