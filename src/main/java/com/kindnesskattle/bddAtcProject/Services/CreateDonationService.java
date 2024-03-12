package com.kindnesskattle.bddAtcProject.Services;

import com.kindnesskattle.bddAtcProject.DTO.DonationPostDetailsDTO;
import com.kindnesskattle.bddAtcProject.DTO.DontationAddressDTO;
import com.kindnesskattle.bddAtcProject.Entities.Address;
import com.kindnesskattle.bddAtcProject.Entities.DonationPost;
import com.kindnesskattle.bddAtcProject.Entities.FoodType;
import com.kindnesskattle.bddAtcProject.Entities.UserAccount;
import com.kindnesskattle.bddAtcProject.Repository.AddressRepository;
import com.kindnesskattle.bddAtcProject.Repository.DonationPostRepository;
import com.kindnesskattle.bddAtcProject.Repository.FoodTypeRepository;
import com.kindnesskattle.bddAtcProject.Repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CreateDonationService {

    @Autowired
    private final UserAccountRepository userAccountRepository;

    @Autowired
    private final FoodTypeRepository foodTypeRepository;

    @Autowired
    private final AddressRepository addressRepository;

    @Autowired
    private final DonationPostRepository donationPostRepository;


    public CreateDonationService(UserAccountRepository userAccountRepository, FoodTypeRepository foodTypeRepository, AddressRepository addressRepository, DonationPostRepository donationPostRepository) {
        this.userAccountRepository = userAccountRepository;
        this.foodTypeRepository = foodTypeRepository;
        this.addressRepository = addressRepository;
        this.donationPostRepository = donationPostRepository;
    }

    @Transactional
    public DonationPost createDonationPost(DontationAddressDTO request) {
        try {
            if (request.getAddressLine() == null || request.getAddressLine().isEmpty() ||
                    request.getPincode() == null || request.getPincode().isEmpty() ||
                    request.getUserId() == null ||  Double.valueOf(request.getLatitude()) == null ||
                    request.getTimeAvailable() == null  || Double.valueOf(request.getLongitude()) == null ||
                    request.getFoodImageUrl() == null
            ) {
                throw new IllegalArgumentException("Missing or invalid input data");
            }

            Address address = new Address();
            address.setAddressLine(request.getAddressLine());
            address.setPincode(request.getPincode());
            address.setLongitude(request.getLongitude());
            address.setLatitude(request.getLatitude());
            Address savedAddress = addressRepository.save(address);

            UserAccount user = userAccountRepository.findById(request.getUserId()).orElse(null);
            FoodType foodType = foodTypeRepository.findById((Long) request.getFoodTypeId()).orElse(null);
            if (user == null || foodType == null) {
                throw new IllegalArgumentException("Invalid user ID or food type ID");
            }

            DonationPost donationPost = new DonationPost();
            donationPost.setUser(user);
            donationPost.setFoodType(foodType);
            donationPost.setAddress(savedAddress); // Set the saved address directly
            donationPost.setFoodImageUrl(request.getFoodImageUrl());
            donationPost.setTimeAvailable(request.getTimeAvailable());
            donationPost.setIsPickupCompleted(false);
            donationPost.setCreatedAt(LocalDateTime.now());
            return donationPostRepository.save(donationPost);
        } catch (Exception e) {
            log.error("An unexpected error occurred while processing the donation post", e);
            throw new IllegalArgumentException("Invalid user ID or food type ID");
        }
    }


    public void deleteDonationPost(Long postId) {
        DonationPost donationPost = donationPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Donation post not found"));

        Address address = donationPost.getAddress();

        try {
            donationPostRepository.delete(donationPost);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new IllegalArgumentException("Cannot delete donation post because it is referenced in another table");
            } else {
                throw e;
            }
        }

        if (address != null) {
            addressRepository.delete(address);
        }
    }

    public DonationPostDetailsDTO getDonationPostDetails(Long postId) {
        DonationPost donationPost = donationPostRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Donation Id post not found"));

        // Create a DTO to hold the details
        DonationPostDetailsDTO detailsDTO = new DonationPostDetailsDTO();
        detailsDTO.setDonationPost(donationPost);

        // Set the associated address and food type
//        detailsDTO.setAddress(donationPost.getAddress());
//        detailsDTO.setFoodType(donationPost.getFoodType());

        return detailsDTO;
    }


    public List<DonationPostDetailsDTO> getAllDonationPostsDetails() {
        List<DonationPost> donationPosts = donationPostRepository.findAll();
        List<DonationPostDetailsDTO> donationPostsDetails = new ArrayList<>();

        if (donationPosts.isEmpty()) {
            return donationPostsDetails; // Return an empty list if no donation posts are available
        }
        for (DonationPost donationPost : donationPosts) {
            DonationPostDetailsDTO detailsDTO = new DonationPostDetailsDTO();
            detailsDTO.setDonationPost(donationPost);
//            detailsDTO.setAddress(donationPost.getAddress());
//            detailsDTO.setFoodType(donationPost.getFoodType());
            donationPostsDetails.add(detailsDTO);
        }

        return donationPostsDetails;
    }

}
