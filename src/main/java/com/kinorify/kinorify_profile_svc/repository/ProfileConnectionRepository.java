package com.kinorify.kinorify_profile_svc.repository;

import com.kinorify.kinorify_profile_svc.entity.ProfileConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionOutgoingDetailsResponseDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileConnectionRepository
        extends JpaRepository<ProfileConnection, UUID> {


    @Query(value = """
            SELECT *
            FROM profile.profile_connection
            WHERE requester_user_id = :userId
               OR recipient_user_id = :userId
            ORDER BY requested_at DESC
            """, nativeQuery = true)
    List<ProfileConnection> findConnectionsByUserId(UUID userId);



@Query(value = """
        SELECT *
        FROM profile.profile_connection
        WHERE (requester_user_id = :userA
           AND recipient_user_id = :userB)
           OR (requester_user_id = :userB
           AND recipient_user_id = :userA)
        """, nativeQuery = true)
Optional<ProfileConnection> findConnectionBetweenUsers(
        UUID userA,
        UUID userB
);



    @Query(value = """
            SELECT pc.*
            FROM profile.profile_connection pc
            INNER JOIN profile.profile_connection_status pcs
                ON pc.profile_connection_status_id = pcs.id
            WHERE (pc.requester_user_id = :userId
                OR pc.recipient_user_id = :userId)
              AND pcs.name = :statusName
            ORDER BY pc.updated_at DESC
            """, nativeQuery = true)
    List<ProfileConnection> findConnectionsByUserIdAndStatus(
            UUID userId,
            String statusName
    );



    @Query(value = """
            SELECT pc.*
            FROM profile.profile_connection pc
            INNER JOIN profile.profile_connection_status pcs
                ON pc.profile_connection_status_id = pcs.id
            WHERE pc.recipient_user_id = :userId
              AND pcs.name = :statusName
            ORDER BY pc.requested_at
            """, nativeQuery = true)
    List<ProfileConnection> findIncomingConnectionsByUserIdAndStatus(
            UUID userId,
            String statusName
    );



    @Query(value = """
            SELECT pc.*
            FROM profile.profile_connection pc
            INNER JOIN profile.profile_connection_status pcs
                ON pc.profile_connection_status_id = pcs.id
            WHERE pc.requester_user_id = :userId
              AND pcs.name = :statusName
            ORDER BY pc.requested_at
            """, nativeQuery = true)
    List<ProfileConnection> findOutgoingConnectionsByUserIdAndStatus(
            UUID userId,
            String statusName
    );


@Query(value = """
        SELECT
            pc.id AS connectionId,
            CASE
                WHEN pc.requester_user_id = :userId
                    THEN pc.recipient_user_id
                ELSE pc.requester_user_id
            END AS userId,
            p.display_name AS displayName,
            pcs.name AS status
        FROM profile.profile_connection pc
        INNER JOIN profile.profile_connection_status pcs
            ON pc.profile_connection_status_id = pcs.id
        INNER JOIN profile.profile p
            ON p.user_id =
                CASE
                    WHEN pc.requester_user_id = :userId
                        THEN pc.recipient_user_id
                    ELSE pc.requester_user_id
                END
        WHERE (
                pc.requester_user_id = :userId
                OR pc.recipient_user_id = :userId
              )
          AND pcs.name = 'ACCEPTED'
        ORDER BY pc.updated_at DESC
        """, nativeQuery = true)
List<ProfileConnectionDetailsProjection>
findAcceptedConnectionDetailsByUserId(UUID userId);




@Query("""
SELECT new com.kinorify.kinorify_profile_svc.dto.response.ProfileConnectionOutgoingDetailsResponseDTO(
    pc.id,
    pc.recipient.userId,
    p.displayName,
    p.email,
    pcs.name,
    pc.requestedAt
)
FROM ProfileConnection pc
JOIN pc.recipient u
JOIN Profile p ON p.userId = u.userId
JOIN pc.status pcs
WHERE pc.requester.userId = :userId
AND pcs.name = 'PENDING'
""")
List<ProfileConnectionOutgoingDetailsResponseDTO> getOutgoingConnectionDetailsByUserId(
        @Param("userId") UUID userId
);

}
