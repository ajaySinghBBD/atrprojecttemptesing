package com.kindnesskattle.bddAtcProject.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DontationAddressDTO {

    private Long userId;
    private Long foodTypeId;
    private String addressLine;
    private String pincode;
    private double longitude;
    private double latitude;
    private String foodImageUrl;
    private LocalDateTime timeAvailable;
//    private boolean isPickupCompleted;
//    private LocalDateTime createdAt;

    public DontationAddressDTO(long userId, Long foodTypeId, String addressLine, String pincode,
                                  double longitude, double latitude, String foodImageUrl,LocalDateTime timeAvailable) {
        this.userId = userId;
        this.foodTypeId = foodTypeId;
        this.addressLine = addressLine;
        this.pincode = pincode;
        this.longitude = longitude;
        this.latitude = latitude;
        this.foodImageUrl = foodImageUrl;
        this.timeAvailable = timeAvailable;
//        this.isPickupCompleted = isPickupCompleted;
//        this.createdAt = createdAt;
//        LocalDateTime timeAvailable, boolean isPickupCompleted,
//        LocalDateTime createdAt
    }


}
