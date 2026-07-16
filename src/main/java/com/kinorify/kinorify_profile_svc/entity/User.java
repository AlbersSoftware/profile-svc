package com.kinorify.kinorify_profile_svc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(schema = "profile", name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID userId;


    @Column(name = "cognito_sub", nullable = false, unique = true, length = 128)
    private String cognitoSub;


    @Column(nullable = false, unique = true, length = 255)
    private String email;


    @Column(name = "email_verified", nullable = false)
    private Boolean emailVerified = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "account_status_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_users_account_status")
    )
    private AccountStatus accountStatus;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;


    @OneToOne(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Profile profile;
}
