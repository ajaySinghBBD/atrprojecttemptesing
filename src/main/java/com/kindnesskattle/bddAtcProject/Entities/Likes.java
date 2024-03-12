package com.kindnesskattle.bddAtcProject.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Data
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private DonationPost post;

    @Column(name = "like_date_time", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime likeDateTime;

    public Likes() {
        // Default constructor
    }

    public Likes(UserAccount user, DonationPost post) {
        this.user = user;
        this.post = post;
    }


}
