package com.kinorify.kinorify_profile_svc.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(schema = "profile", name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @Column(name = "user_id")
    private UUID userId;


    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(
        name = "user_id",
        foreignKey = @ForeignKey(name = "fk_profile_user")
    )
    private User user;


    @Column(name = "display_name", nullable = false, length = 100)
    private String displayName;


    @Column(length = 30)
    private String phone;


    @Column(length = 255)
    private String email;


    @Column(name = "avatar_media_id")
    private UUID avatarMediaId;


    @Column(length = 100)
    private String timezone;


    private LocalDate birthday;


    @Column(length = 500)
    private String bio;


    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
