package com.kinorify.kinorify_profile_svc.repository;

import com.kinorify.kinorify_profile_svc.entity.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountStatusRepository
        extends JpaRepository<AccountStatus, Short> {

    @Query(value = """
            SELECT *
            FROM profile.account_status
            ORDER BY id
            """, nativeQuery = true)
    List<AccountStatus> findAllStatuses();

    @Query(value = """
        SELECT s.*
        FROM profile.account_status s
        INNER JOIN profile.users u
            ON s.id = u.account_status_id
        WHERE u.user_id = :userId
        """, nativeQuery = true)
Optional<AccountStatus> findStatusByUserId(UUID userId);

}
