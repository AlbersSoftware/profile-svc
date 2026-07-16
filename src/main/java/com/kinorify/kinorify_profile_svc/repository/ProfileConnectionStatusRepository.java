package com.kinorify.kinorify_profile_svc.repository;


import com.kinorify.kinorify_profile_svc.entity.ProfileConnectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileConnectionStatusRepository
        extends JpaRepository<ProfileConnectionStatus, Short> {

    @Query(value = """
            SELECT *
            FROM profile.profile_connection_status
            ORDER BY id
            """, nativeQuery = true)
    List<ProfileConnectionStatus> findAllStatuses();

    @Query(value = """
        SELECT pcs.*
        FROM profile.profile_connection_status pcs
        INNER JOIN profile.profile_connection pc
            ON pcs.id = pc.profile_connection_status_id
        WHERE pc.requester_user_id = :requesterUserId
          AND pc.recipient_user_id = :recipientUserId
        """, nativeQuery = true)
Optional<ProfileConnectionStatus> findStatusBetweenUsers(
        UUID requesterUserId,
        UUID recipientUserId
);

}
