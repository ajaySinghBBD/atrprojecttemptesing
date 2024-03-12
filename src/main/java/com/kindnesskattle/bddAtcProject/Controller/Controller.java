package com.kindnesskattle.bddAtcProject.Controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kindnesskattle.bddAtcProject.DTO.CommentRequestDTO;
import com.kindnesskattle.bddAtcProject.DTO.DonationPostDetailsDTO;
import com.kindnesskattle.bddAtcProject.DTO.DontationAddressDTO;
import com.kindnesskattle.bddAtcProject.DTO.LikesSummaryDTO;
import com.kindnesskattle.bddAtcProject.Entities.Comment;
import com.kindnesskattle.bddAtcProject.Entities.DonationPost;
import com.kindnesskattle.bddAtcProject.Repository.CommentRepository;
import com.kindnesskattle.bddAtcProject.Services.CommentService;
import com.kindnesskattle.bddAtcProject.Services.CreateDonationService;
import com.kindnesskattle.bddAtcProject.Services.FetchLikesService;
import com.kindnesskattle.bddAtcProject.Services.LikesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping("/")
@Slf4j
public class Controller {

    @Autowired
    private final LikesService likesService;
    private final FetchLikesService fetchLikesService;

    @Autowired
    CreateDonationService createDonationPost;

    @Autowired
    CommentService commentService;

    public Controller(LikesService likesService,FetchLikesService fetchLikesService) {
        this.likesService = likesService;
        this.fetchLikesService = fetchLikesService;
    }

    @GetMapping("/home")
    public ResponseEntity<String> home() {
        System.out.println("Welcome to kindnessKettle");
        return ResponseEntity.ok("Welcome to kindnessKettle");
    }



    @PostMapping("/addLikes")
    public ResponseEntity<String> addLike(@RequestParam Long userId, @RequestParam Long postId) {
        try {

            log.info("Log message :- userID= "+userId +"PostID = "+ postId);
            likesService.addLike(userId, postId);

            return ResponseEntity.ok("Like added successfully.");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/getLikes")
    public ResponseEntity<List<LikesSummaryDTO>> getLikes(@RequestParam Long postId) {
        try {
            log.info("Log message :- PostID = " + postId);

            // Call FetchLikesService to retrieve likes information
            List<LikesSummaryDTO> likesSummaryList = fetchLikesService.getLikesSummaryByPostId(postId);

            Long totalLikes = Long.valueOf(likesSummaryList.size());

            likesSummaryList.forEach(dto -> dto.setTotalLikes(totalLikes));
            return ResponseEntity.ok(likesSummaryList);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @GetMapping("/checkingPincode/{pincode}")
    public ResponseEntity<?> pincodechecking(@PathVariable String pincode) {
        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "http://www.postalpincode.in/api/pincode/" + pincode;

        String response;
        try {
            response = restTemplate.getForObject(apiUrl, String.class);
            System.out.println(response);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while calling the external API");
        }

        if (response != null && !response.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);
                String status = jsonNode.get("Status").asText();

                if ("Error".equals(status)) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());

                } else {

                    return ResponseEntity.ok("Success: " + response); // Adjust the response format as needed
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Empty response from the external API");
        }
    }


    @PostMapping("/donationPosts")
    public ResponseEntity<String> createDonationPost(@RequestBody DontationAddressDTO request) {
        try {
            DonationPost donationPost = createDonationPost.createDonationPost(request);
            return ResponseEntity.ok("Donation post added successfully");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to add donation post: " + e.getMessage());
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/donationPosts/{postId}")
    public ResponseEntity<String> deleteDonationPost(@PathVariable Long postId) {
        try {
            createDonationPost.deleteDonationPost(postId);
            return ResponseEntity.ok("Donation post and associated address deleted successfully");
        } catch (IllegalArgumentException e) {
            throw new  IllegalArgumentException(e.getMessage());
        }
    }

    @GetMapping("/fetchDonationPosts/{postId}")
    public ResponseEntity<DonationPostDetailsDTO> getDonationPostDetails(@PathVariable Long postId) {
        try {
            DonationPostDetailsDTO detailsDTO = createDonationPost.getDonationPostDetails(postId);
            return ResponseEntity.ok(detailsDTO);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @GetMapping("/fetchAllDonationPosts")
    public ResponseEntity<List<DonationPostDetailsDTO>> getAllDonationPostsDetails() {
        List<DonationPostDetailsDTO> donationPostsDetails = createDonationPost.getAllDonationPostsDetails();
        return ResponseEntity.ok(donationPostsDetails);
    }

    @PostMapping("/comments")
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequestDTO commentRequest) {
        Comment comment = commentService.addComment(commentRequest.getUserId(), commentRequest.getPostId(), commentRequest.getCommentContent());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete_comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Long commentId) {
        try {
            boolean deleted = commentService.deleteComment(commentId);
            if (deleted) {
                return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Comment not found or could not be deleted", HttpStatus.NOT_FOUND);
            }
        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
            throw new EntityNotFoundException(e.getMessage());
        } catch (RuntimeException e) {
//            return new ResponseEntity<>("Error deleting comment: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            throw new RuntimeException(e.getMessage());
        }
    }

//    @GetMapping("/comments/post/{postId}")
//    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable("postId") long postId) {
//        List<Comment> comments = commentService.getCommentsByPostId(postId);
//        if (!comments.isEmpty()) {
//            return new ResponseEntity<>(comments, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

}





