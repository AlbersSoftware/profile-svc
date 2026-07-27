package com.kinorify.kinorify_profile_svc.rest;


import com.kinorify.kinorify_profile_svc.config.JwtService;
import com.kinorify.kinorify_profile_svc.dto.request.RequestConnectionRequestDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionResponseDTO;
import com.kinorify.kinorify_profile_svc.entity.User;
import com.kinorify.kinorify_profile_svc.repository.UserRepository;
import com.kinorify.kinorify_profile_svc.service.ProfileConnectionService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/profile-connections")
@RequiredArgsConstructor
public class ProfileConnectionController {


    private static final Logger log =
            LoggerFactory.getLogger(
                    ProfileConnectionController.class
            );


    private final ProfileConnectionService profileConnectionService;


    private final JwtService jwtService;


    private final UserRepository userRepository;



    private UUID getAuthenticatedUserId(
            Jwt jwt) {


        String cognitoSub =
                jwtService.getCognitoSub(jwt);


        User user =
                userRepository
                        .findByCognitoSub(cognitoSub)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Authenticated user not found."
                                ));


        return user.getUserId();
    }





    @PostMapping
    public ResponseEntity<ProfileConnectionResponseDTO> requestConnection(
            @RequestBody RequestConnectionRequestDTO request,
            @AuthenticationPrincipal Jwt jwt) {


        UUID requesterUserId =
                getAuthenticatedUserId(jwt);


        request.setRequesterUserId(
                requesterUserId
        );


        log.info(
                "User {} requested connection with user {}.",
                requesterUserId,
                request.getRecipientUserId()
        );


        return ResponseEntity.ok(
                profileConnectionService.requestConnection(
                        request.getRequesterUserId(),
                        request.getRecipientUserId()
                )
        );
    }





    @PutMapping("/{connectionId}/accept")
    public ResponseEntity<ProfileConnectionResponseDTO> acceptConnection(
            @PathVariable UUID connectionId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} accepting connection {}.",
                getAuthenticatedUserId(jwt),
                connectionId
        );


        return ResponseEntity.ok(
                profileConnectionService.acceptConnection(
                        connectionId
                )
        );
    }





    @PutMapping("/{connectionId}/decline")
    public ResponseEntity<ProfileConnectionResponseDTO> declineConnection(
            @PathVariable UUID connectionId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} declining connection {}.",
                getAuthenticatedUserId(jwt),
                connectionId
        );


        return ResponseEntity.ok(
                profileConnectionService.declineConnection(
                        connectionId
                )
        );
    }





    @PutMapping("/{connectionId}/remove")
    public ResponseEntity<ProfileConnectionResponseDTO> removeConnection(
            @PathVariable UUID connectionId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} removing connection {}.",
                getAuthenticatedUserId(jwt),
                connectionId
        );


        return ResponseEntity.ok(
                profileConnectionService.removeConnection(
                        connectionId
                )
        );
    }





    /**
     * Placeholder until blocking rules are finalized.
     */
    @PutMapping("/{connectionId}/block")
    public ResponseEntity<ProfileConnectionResponseDTO> blockConnection(
            @PathVariable UUID connectionId) {


        return ResponseEntity.ok(
                profileConnectionService.blockConnection(
                        connectionId
                )
        );
    }





    @GetMapping("/{connectionId}")
    public ResponseEntity<ProfileConnectionResponseDTO> getConnectionById(
            @PathVariable UUID connectionId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving connection {}.",
                getAuthenticatedUserId(jwt),
                connectionId
        );


        return profileConnectionService
                .getConnectionById(connectionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    @GetMapping("/users")
    public ResponseEntity<ProfileConnectionResponseDTO> getConnectionByUsers(
            @RequestParam UUID requesterUserId,
            @RequestParam UUID recipientUserId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving connection between {} and {}.",
                getAuthenticatedUserId(jwt),
                requesterUserId,
                recipientUserId
        );


        return profileConnectionService
                .getConnectionByUsers(
                        requesterUserId,
                        recipientUserId
                )
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }





    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getConnectionsByUserId(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving connections for user {}.",
                getAuthenticatedUserId(jwt),
                userId
        );


        return ResponseEntity.ok(
                profileConnectionService
                        .getConnectionsByUserId(userId)
        );
    }





    @GetMapping("/user/{userId}/incoming")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getIncomingRequestsByUserId(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving incoming requests for user {}.",
                getAuthenticatedUserId(jwt),
                userId
        );


        return ResponseEntity.ok(
                profileConnectionService
                        .getIncomingRequestsByUserId(userId)
        );
    }





    @GetMapping("/user/{userId}/outgoing")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getOutgoingRequestsByUserId(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving outgoing requests for user {}.",
                getAuthenticatedUserId(jwt),
                userId
        );


        return ResponseEntity.ok(
                profileConnectionService
                        .getOutgoingRequestsByUserId(userId)
        );
    }





    @GetMapping("/user/{userId}/accepted")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getAcceptedConnectionsByUserId(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving accepted connections for user {}.",
                getAuthenticatedUserId(jwt),
                userId
        );


        return ResponseEntity.ok(
                profileConnectionService
                        .getAcceptedConnectionsByUserId(userId)
        );
    }





    @GetMapping("/user/{userId}/declined")
    public ResponseEntity<List<ProfileConnectionResponseDTO>>
    getDeclinedConnectionsByUserId(
            @PathVariable UUID userId,
            @AuthenticationPrincipal Jwt jwt) {


        log.info(
                "User {} retrieving declined connections for user {}.",
                getAuthenticatedUserId(jwt),
                userId
        );


        return ResponseEntity.ok(
                profileConnectionService
                        .getDeclinedConnectionsByUserId(userId)
        );
    }

}
