package com.kindnesskattle.bddAtcProject.Services;

import com.kindnesskattle.bddAtcProject.Entities.Likes;
import com.kindnesskattle.bddAtcProject.DTO.LikesSummaryDTO;
import com.kindnesskattle.bddAtcProject.Repository.DonationPostRepository;
import com.kindnesskattle.bddAtcProject.Repository.LikesRepository;
import com.kindnesskattle.bddAtcProject.Repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FetchLikesService {

    private final LikesRepository likesRepository;
    private final UserAccountRepository userAccountRepository;
    private final DonationPostRepository donationPostRepository;



    @Autowired
    public FetchLikesService(LikesRepository likesRepository, UserAccountRepository userAccountRepository, DonationPostRepository donationPostRepository) {
        this.likesRepository = likesRepository;
        this.userAccountRepository = userAccountRepository;
        this.donationPostRepository = donationPostRepository;
    }

    public List<LikesSummaryDTO> getLikesSummaryByPostId(Long postId) {

        List<Likes> likesList = likesRepository.findAllByPost_PostId(postId);



        // Create LikesSummaryDTO instances
        return likesList.stream()
                .map(Likes->createLikesSummaryDTO(Likes))
                .collect(Collectors.toList());
    }

    private LikesSummaryDTO createLikesSummaryDTO(Likes likes ) {

        Long postId = likes.getPost().getPostId();

        return new LikesSummaryDTO(likes.getUser(), postId);
    }
}

