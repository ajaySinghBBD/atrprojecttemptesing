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
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreateDonationServiceTest {

    @MockBean
    DonationPostRepository donationPostRepository;

    @MockBean
    UserAccountRepository userAccountRepository;
    @MockBean
    AddressRepository addressRepository;

    @MockBean
    private FoodTypeRepository foodTypeRepository;

    @Autowired
    CreateDonationService createDonationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateDonationPost() {
        // Mock data
        DontationAddressDTO request = new DontationAddressDTO(
                1L,
                1L,
                "Test Address",
                "123456",
                1.0,
                2.0,
                "test.jpg",
                LocalDateTime.now()
        );

        UserAccount user = new UserAccount();
        user.setUserId(1L);
        FoodType foodType = new FoodType();
        foodType.setFoodId(1L);
        Address address = new Address();
        address.setAddressId(1L);

        // Mock repository calls
        when(userAccountRepository.findById(1L)).thenReturn(Optional.of(user));
        when(foodTypeRepository.findById(1L)).thenReturn(Optional.of(foodType));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // Call the method to test
        createDonationService.createDonationPost(request);

        // Verify
        verify(userAccountRepository, times(1)).findById(1L);
        verify(foodTypeRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(any(Address.class));
        verify(donationPostRepository, times(1)).save(any(DonationPost.class));
    }

    @Test
    void deleteDonationPost() {
        // Creating Objects
        Address add = new Address();
        add.setAddressId(1l);
        add.setAddressLine("Pune");

        DonationPost donationPost = new DonationPost();
        donationPost.setPostId(123L);
        donationPost.setAddress(add);

        // Mock
        when(donationPostRepository.findById(123L)).thenReturn(Optional.of(donationPost));

//        when(donationPostRepository.findById(123L)).thenThrow(new Exception("Error"));

//        when(donationPostRepository.delete(donationPost)).thenReturn(null);

        doNothing().when(donationPostRepository).delete(isA(DonationPost.class));

        doNothing().when(addressRepository).delete(isA(Address.class));

        // Actual call
        createDonationService.deleteDonationPost(123L);

        // Verify
        verify(donationPostRepository, times(1)).findById(123L);
//        verify(donationPostRepository.delete(donationPost), times(1));

    }

    @Test
    void deleteDonationPost_ThrowException() {
        // Creating Objects
        Address add = new Address();
        add.setAddressId(1l);
        add.setAddressLine("Pune");

        DonationPost donationPost = new DonationPost();
        donationPost.setPostId(123L);
        donationPost.setAddress(add);

        // Mock
        when(donationPostRepository.findById(123L)).thenThrow(new IllegalArgumentException("Error"));

        doNothing().when(donationPostRepository).delete(isA(DonationPost.class));

        doNothing().when(addressRepository).delete(isA(Address.class));

        // Actual call
        try {
            createDonationService.deleteDonationPost(123L);
            fail("Exception not thrown");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        // Verify
        verify(donationPostRepository, times(1)).findById(123L);
//        verify(donationPostRepository.delete(donationPost), times(1));

    }



    @Test
    void getDonationPostDetails_WhenNotExists_ThrowsIllegalArgumentException()
    {
        DonationPost donationPost = new DonationPost();
        donationPost.setPostId(2L);

        when(donationPostRepository.findById(2L)).thenThrow(new IllegalArgumentException("Error"));

        try{
            createDonationService.deleteDonationPost(2L);
            fail("Exception not thrown");
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        //verify

        verify(donationPostRepository, times(1)).findById(2L);
    }


    @Test
    void getAllDonationPostsDetails() {
    }




}