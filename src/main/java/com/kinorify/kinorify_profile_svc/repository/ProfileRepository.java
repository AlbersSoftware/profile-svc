package com.kinorify.kinorify_profile_svc.repository;


import com.kinorify.kinorify_profile_svc.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository
        extends JpaRepository<Profile, UUID> {

    @Query(value = """
            SELECT *
            FROM profile.profile
            WHERE user_id = :userId
            """, nativeQuery = true)
    Optional<Profile> findByUserId(UUID userId);

    @Query(value = """
        SELECT *
        FROM profile.profile
        ORDER BY display_name
        """, nativeQuery = true)
List<Profile> findAllProfiles();

}
