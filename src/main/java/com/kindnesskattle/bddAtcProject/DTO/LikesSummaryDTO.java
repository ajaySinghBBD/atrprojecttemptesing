package com.kindnesskattle.bddAtcProject.DTO;

import com.kindnesskattle.bddAtcProject.Entities.UserAccount;
import lombok.Data;

@Data
public class LikesSummaryDTO {

    private UserAccount user;
    private Long postId;
    private Long totalLikes;

    public LikesSummaryDTO(UserAccount user, Long postId) {
        this.user = user;
        this.postId = postId;

    }
}
