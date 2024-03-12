package com.kindnesskattle.bddAtcProject.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "donation_posts")
@Data
public class DonationPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "food_type_id", nullable = false)
    private FoodType foodType;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "food_image_url", length = 2048)
    private String foodImageUrl;

    @Column(name = "time_available", nullable = false)
    private LocalDateTime timeAvailable;

    @Column(name = "is_pickup_completed", nullable = false)
    private Boolean isPickupCompleted = false;

    @Column(name = "created_at", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;



}


