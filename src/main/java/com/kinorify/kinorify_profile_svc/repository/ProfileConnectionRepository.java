package com.kinorify.kinorify_profile_svc.repository;

import com.kinorify.kinorify_profile_svc.entity.ProfileConnection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

}
