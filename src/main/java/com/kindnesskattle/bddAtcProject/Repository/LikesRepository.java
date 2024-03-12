package com.kindnesskattle.bddAtcProject.Repository;

import com.kindnesskattle.bddAtcProject.Entities.DonationPost;
import com.kindnesskattle.bddAtcProject.Entities.Likes;
import com.kindnesskattle.bddAtcProject.Entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    Optional<Likes> findByUserAndPost(UserAccount user, DonationPost post);

    List<Likes> findAllByPost_PostId(Long postId);


}


