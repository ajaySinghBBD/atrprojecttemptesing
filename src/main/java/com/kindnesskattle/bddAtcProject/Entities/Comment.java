package com.kindnesskattle.bddAtcProject.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int comment_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user_id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private DonationPost donation_post;

    @Column(name = "comment_content", length = 120, nullable = false)
    private String comment_content;

    @Column(name = "comment_date_time")
    private Date comment_date_time;



    public Comment(int comment_id, UserAccount user, DonationPost donationPost, String commentContent, Date commentDateTime) {
        this.comment_id = comment_id;
        this.user_id = user;
        this.donation_post = donationPost;
        this.comment_content = commentContent;
        this.comment_date_time = commentDateTime;
    }

}
