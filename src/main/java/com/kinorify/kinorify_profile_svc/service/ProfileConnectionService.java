package com.kinorify.kinorify_profile_svc.service;

import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileConnectionService {


    ProfileConnectionResponseDTO requestConnection(
            UUID requesterUserId,
            UUID recipientUserId
    );


    ProfileConnectionResponseDTO acceptConnection(
            UUID connectionId
    );


    ProfileConnectionResponseDTO declineConnection(
            UUID connectionId
    );


    ProfileConnectionResponseDTO removeConnection(
            UUID connectionId
    );


    ProfileConnectionResponseDTO blockConnection(
            UUID connectionId
    );


    Optional<ProfileConnectionResponseDTO> getConnectionById(
            UUID connectionId
    );


    Optional<ProfileConnectionResponseDTO> getConnectionByUsers(
            UUID requesterUserId,
            UUID recipientUserId
    );


    List<ProfileConnectionResponseDTO> getConnectionsByUserId(
            UUID userId
    );


    List<ProfileConnectionResponseDTO> getIncomingRequestsByUserId(
            UUID userId
    );


    List<ProfileConnectionResponseDTO> getOutgoingRequestsByUserId(
            UUID userId
    );


    List<ProfileConnectionResponseDTO> getAcceptedConnectionsByUserId(
            UUID userId
    );


    List<ProfileConnectionResponseDTO> getDeclinedConnectionsByUserId(
            UUID userId
    );

}
