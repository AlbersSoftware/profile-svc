package com.kinorify.kinorify_profile_svc.repository;

import com.kinorify.kinorify_profile_svc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository
        extends JpaRepository<User, UUID> {

    @Query(value = """
            SELECT *
            FROM profile.users
            ORDER BY created_at
            """, nativeQuery = true)
    List<User> findAllUsers();


    @Query(value = """
            SELECT *
            FROM profile.users
            WHERE user_id = :userId
            """, nativeQuery = true)
    Optional<User> findUserById(UUID userId);


    @Query(value = """
            SELECT *
            FROM profile.users
            WHERE cognito_sub = :cognitoSub
            """, nativeQuery = true)
    Optional<User> findByCognitoSub(String cognitoSub);


    @Query(value = """
            SELECT *
            FROM profile.users
            WHERE email = :email
            """, nativeQuery = true)
    Optional<User> findByEmail(String email);



}
