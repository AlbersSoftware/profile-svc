package com.kinorify.kinorify_profile_svc.repository;


import com.kinorify.kinorify_profile_svc.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

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



    @Query("""
        SELECT p
        FROM Profile p
        WHERE LOWER(p.displayName)
        LIKE LOWER(CONCAT('%', :query, '%'))
        """)
Page<Profile> searchProfiles(
        @Param("query") String query,
        Pageable pageable
);


boolean existsByDisplayName(String displayName);

boolean existsByDisplayNameAndUserIdNot(String displayName, UUID userId );

boolean existsByDisplayNameIgnoreCase(
        String displayName);

boolean existsByDisplayNameIgnoreCaseAndUserIdNot(
        String displayName,
        UUID userId);

}
