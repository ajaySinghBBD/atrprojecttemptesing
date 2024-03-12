package com.kindnesskattle.bddAtcProject.Entities;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Entity
@Table(name = "user_accounts")
@Data
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "image_url", length = 2083)
    private String imageUrl;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "profile_description", length = 120)
    private String profileDescription;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "email_notification_enabled", nullable = false)
    private Boolean emailNotificationEnabled = false;

}


