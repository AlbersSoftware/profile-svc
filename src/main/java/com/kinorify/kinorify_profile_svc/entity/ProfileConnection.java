package com.kinorify.kinorify_profile_svc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
    schema = "profile",
    name = "profile_connection",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uq_profile_connection",
            columnNames = {
                "requester_user_id",
                "recipient_user_id"
            }
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileConnection {

    @Id
    @GeneratedValue
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "requester_user_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_profile_connection_requester"
        )
    )
    private User requester;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "recipient_user_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_profile_connection_recipient"
        )
    )
    private User recipient;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "profile_connection_status_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_profile_connection_status"
        )
    )
    private ProfileConnectionStatus status;


    @CreationTimestamp
    @Column(name = "requested_at", nullable = false)
    private OffsetDateTime requestedAt;


    @Column(name = "accepted_at")
    private OffsetDateTime acceptedAt;


    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
