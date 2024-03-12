package com.kindnesskattle.bddAtcProject.Services;

import com.kindnesskattle.bddAtcProject.DTO.CommentRequestDTO;
import com.kindnesskattle.bddAtcProject.Entities.Comment;
import com.kindnesskattle.bddAtcProject.Entities.DonationPost;
import com.kindnesskattle.bddAtcProject.Entities.UserAccount;
import com.kindnesskattle.bddAtcProject.Repository.CommentRepository;
import com.kindnesskattle.bddAtcProject.Repository.DonationPostRepository;
import com.kindnesskattle.bddAtcProject.Repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {


    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserAccountRepository userRepository;

    @Autowired
    DonationPostRepository donationPostRepository;
    public Comment addComment(long userId, long postId, String commentContent) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        DonationPost donationPost = donationPostRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Donation post not found with id: " + postId));


        Comment comment = new Comment();
        comment.setUser_id(user);
        comment.setDonation_post(donationPost);
        comment.setComment_content(commentContent);
//        comment.setCommentDateTime(new Date());

        return commentRepository.save(comment);
    }


    public boolean deleteComment(Long commentId) {
//            Optional<Comment> comment = commentRepository.findById(commentId);
        Comment comments = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not Found by this id: " + commentId));
        try {
            commentRepository.deleteById(commentId);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Comment with id " + commentId + " not found");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting comment with id " + commentId, e);
        }
    }


}
