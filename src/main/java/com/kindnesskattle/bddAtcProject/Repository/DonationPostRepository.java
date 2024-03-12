package com.kindnesskattle.bddAtcProject.Repository;


import com.kindnesskattle.bddAtcProject.Entities.DonationPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationPostRepository extends JpaRepository<DonationPost, Long> {

}
