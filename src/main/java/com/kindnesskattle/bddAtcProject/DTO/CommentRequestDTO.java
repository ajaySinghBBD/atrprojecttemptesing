package com.kindnesskattle.bddAtcProject.DTO;


import lombok.Data;

@Data
public class CommentRequestDTO {
    private long userId;
    private long postId;
    private String commentContent;


    public CommentRequestDTO() {
    }

    public CommentRequestDTO(long userId, long postId, String commentContent) {
        this.userId = userId;
        this.postId = postId;
        this.commentContent = commentContent;
    }


}
