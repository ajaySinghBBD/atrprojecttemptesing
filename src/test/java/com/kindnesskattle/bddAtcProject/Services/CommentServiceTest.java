package com.kindnesskattle.bddAtcProject.Services;

import com.kindnesskattle.bddAtcProject.Entities.Comment;
import com.kindnesskattle.bddAtcProject.Entities.DonationPost;
import com.kindnesskattle.bddAtcProject.Entities.UserAccount;
import com.kindnesskattle.bddAtcProject.Repository.CommentRepository;
import com.kindnesskattle.bddAtcProject.Repository.DonationPostRepository;
import com.kindnesskattle.bddAtcProject.Repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserAccountRepository userRepository;

    @Mock
    private DonationPostRepository donationPostRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    public void addComment_UserNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        long userId = 1L;
        long postId = 1L;
        String commentContent = "Test comment";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            commentService.addComment(userId, postId, commentContent);
        });

        verify(userRepository, times(1)).findById(userId);
        verifyNoMoreInteractions(userRepository, commentRepository, donationPostRepository);
    }

    @Test
    public void addComment_DonationPostNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        long userId = 1L;
        long postId = 1L;
        String commentContent = "Test comment";

        when(userRepository.findById(userId)).thenReturn(Optional.of(new UserAccount()));
        when(donationPostRepository.findById(postId)).thenReturn(Optional.empty());


        assertThrows(EntityNotFoundException.class, () -> {
            commentService.addComment(userId, postId, commentContent);
        });

        verify(userRepository, times(1)).findById(userId);
        verify(donationPostRepository, times(1)).findById(postId);
        verifyNoMoreInteractions(userRepository, commentRepository, donationPostRepository);
    }

    @Test
    public void addComment_ValidInput_CommentSavedSuccessfully() {

        long userId = 1L;
        long postId = 1L;
        String commentContent = "Test comment";
        UserAccount user = new UserAccount();
        DonationPost donationPost = new DonationPost();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(donationPostRepository.findById(postId)).thenReturn(Optional.of(donationPost));
        when(commentRepository.save(any(Comment.class))).thenReturn(new Comment());

        Comment result = commentService.addComment(userId, postId, commentContent);


        verify(userRepository, times(1)).findById(userId);
        verify(donationPostRepository, times(1)).findById(postId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }
}
