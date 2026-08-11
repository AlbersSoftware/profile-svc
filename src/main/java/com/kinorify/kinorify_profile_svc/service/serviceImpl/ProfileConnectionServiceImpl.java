package com.kinorify.kinorify_profile_svc.service.impl;

import com.kinorify.kinorify_profile_svc.entity.ProfileConnection;
import com.kinorify.kinorify_profile_svc.entity.ProfileConnectionStatus;
import com.kinorify.kinorify_profile_svc.enums.ProfileConnectionStatusType;
import com.kinorify.kinorify_profile_svc.repository.ProfileConnectionRepository;
import com.kinorify.kinorify_profile_svc.repository.ProfileConnectionStatusRepository;
import com.kinorify.kinorify_profile_svc.repository.UserRepository;
import com.kinorify.kinorify_profile_svc.service.ProfileConnectionService;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionOutgoingDetailsResponseDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionDetailsResponseDTO;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProfileConnectionServiceImpl
        implements ProfileConnectionService {


    private final ProfileConnectionRepository connectionRepository;

    private final ProfileConnectionStatusRepository statusRepository;

    private final UserRepository userRepository;



@Override
@Transactional
public ProfileConnectionResponseDTO requestConnection(
        UUID requesterUserId,
        UUID recipientUserId) {

    if (requesterUserId.equals(recipientUserId)) {
        throw new IllegalArgumentException(
                "Users cannot connect with themselves.");
    }

    userRepository.findUserById(requesterUserId)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Requester does not exist."));

    userRepository.findUserById(recipientUserId)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Recipient does not exist."));

if (connectionRepository
        .findConnectionBetweenUsers(
                requesterUserId,
                recipientUserId)
        .isPresent()) {

    throw new IllegalStateException(
            "A connection already exists between these users.");
}

    ProfileConnectionStatus pending =
            statusRepository.findById(
                    ProfileConnectionStatusType.PENDING.getId())
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "PENDING status missing."));

    ProfileConnection connection =
            new ProfileConnection();

    connection.setRequester(
            userRepository.findUserById(requesterUserId).get());

    connection.setRecipient(
            userRepository.findUserById(recipientUserId).get());

    connection.setStatus(pending);

    ProfileConnection savedConnection =
            connectionRepository.save(connection);

    return mapToResponse(savedConnection);
}



@Override
@Transactional
public ProfileConnectionResponseDTO acceptConnection(
        UUID connectionId) {

    ProfileConnection connection =
            connectionRepository.findById(connectionId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Connection not found."));

    if (connection.getStatus().getId()
            == ProfileConnectionStatusType.BLOCKED.getId()) {

        throw new IllegalStateException(
                "Blocked connections cannot be accepted.");
    }

    ProfileConnectionStatus accepted =
            statusRepository.findById(
                    ProfileConnectionStatusType.ACCEPTED.getId())
                    .orElseThrow();

    connection.setStatus(accepted);

    connection.setAcceptedAt(
            OffsetDateTime.now());

    return mapToResponse(
            connectionRepository.save(connection));
}



@Override
@Transactional
public ProfileConnectionResponseDTO declineConnection(
        UUID connectionId) {

    ProfileConnection connection =
            connectionRepository.findById(connectionId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Connection not found."));

    if (connection.getStatus().getId()
            == ProfileConnectionStatusType.BLOCKED.getId()) {

        throw new IllegalStateException(
                "Blocked connections cannot be declined.");
    }

    ProfileConnectionStatus declined =
            statusRepository.findById(
                    ProfileConnectionStatusType.DECLINED.getId())
                    .orElseThrow();

    connection.setStatus(declined);

    return mapToResponse(
            connectionRepository.save(connection));
}



@Override
@Transactional
public ProfileConnectionResponseDTO removeConnection(UUID connectionId) {

    ProfileConnection connection =
            connectionRepository.findById(connectionId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Connection not found."));

    ProfileConnectionStatus removed =
            statusRepository.findById(
                    ProfileConnectionStatusType.REMOVED.getId())
                    .orElseThrow();

    connection.setStatus(removed);

    return mapToResponse(
            connectionRepository.save(connection));
}



@Override
@Transactional
public ProfileConnectionResponseDTO blockConnection(UUID connectionId) {

    throw new UnsupportedOperationException(
            "Blocking is not implemented yet.");
}



@Override
@Transactional(readOnly = true)
public Optional<ProfileConnectionResponseDTO> getConnectionById(
        UUID connectionId) {

    return connectionRepository.findById(connectionId)
            .map(this::mapToResponse);
}



@Override
@Transactional(readOnly = true)
public Optional<ProfileConnectionResponseDTO> getConnectionByUsers(
        UUID requesterUserId,
        UUID recipientUserId) {

    return connectionRepository
            .findConnectionBetweenUsers(
                    requesterUserId,
                    recipientUserId)
            .map(this::mapToResponse);
}



@Override
@Transactional(readOnly = true)
public List<ProfileConnectionResponseDTO> getConnectionsByUserId(
        UUID userId) {

    return connectionRepository
            .findConnectionsByUserId(userId)
            .stream()
            .map(this::mapToResponse)
            .toList();
}



@Override
@Transactional(readOnly = true)
public List<ProfileConnectionResponseDTO> getIncomingRequestsByUserId(
        UUID userId) {

    return connectionRepository
            .findIncomingConnectionsByUserIdAndStatus(
                    userId,
                    ProfileConnectionStatusType.PENDING.name()
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}



@Override
@Transactional(readOnly = true)
public List<ProfileConnectionResponseDTO> getOutgoingRequestsByUserId(
        UUID userId) {

    return connectionRepository
            .findOutgoingConnectionsByUserIdAndStatus(
                    userId,
                    ProfileConnectionStatusType.PENDING.name()
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}



@Override
@Transactional(readOnly = true)
public List<ProfileConnectionResponseDTO> getAcceptedConnectionsByUserId(
        UUID userId) {

    return connectionRepository
            .findConnectionsByUserIdAndStatus(
                    userId,
                    ProfileConnectionStatusType.ACCEPTED.name()
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}



@Override
@Transactional(readOnly = true)
public List<ProfileConnectionResponseDTO> getDeclinedConnectionsByUserId(
        UUID userId) {

    return connectionRepository
            .findConnectionsByUserIdAndStatus(
                    userId,
                    ProfileConnectionStatusType.DECLINED.name()
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
}



@Override
@Transactional(readOnly = true)
public List<ProfileConnectionDetailsResponseDTO>
getAcceptedConnectionDetailsByUserId(UUID userId) {

    return connectionRepository
            .findAcceptedConnectionDetailsByUserId(userId)
            .stream()
            .map(connection ->
                    new ProfileConnectionDetailsResponseDTO(
                            connection.getConnectionId(),
                            connection.getUserId(),
                            connection.getDisplayName(),
                            connection.getStatus()
                    )
            )
            .toList();
}

@Override
@Transactional(readOnly = true)
public List<ProfileConnectionOutgoingDetailsResponseDTO>
getOutgoingConnectionDetailsByUserId(UUID userId) {

    return connectionRepository
            .getOutgoingConnectionDetailsByUserId(
                    userId
            );
}



  private ProfileConnectionResponseDTO mapToResponse(ProfileConnection connection) {

    ProfileConnectionResponseDTO dto =
            new ProfileConnectionResponseDTO();

    dto.setConnectionId(connection.getId());

    dto.setRequesterUserId(
            connection.getRequester().getUserId());

    dto.setRecipientUserId(
            connection.getRecipient().getUserId());

    dto.setStatus(
            connection.getStatus().getName());

    dto.setRequestedAt(
            connection.getRequestedAt());

    dto.setAcceptedAt(
            connection.getAcceptedAt());

    dto.setUpdatedAt(
            connection.getUpdatedAt());

    return dto;
}
}
