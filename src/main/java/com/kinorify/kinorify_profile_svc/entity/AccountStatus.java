package com.kinorify.kinorify_profile_svc.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(schema = "profile", name = "account_status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatus {

    @Id
    private Short id;

    @Column(nullable = false, unique = true, length = 30)
    private String name;
}
