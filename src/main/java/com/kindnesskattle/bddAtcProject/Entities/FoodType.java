package com.kindnesskattle.bddAtcProject.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "food_types")
@Data
public class FoodType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long foodId;

    @Column(name = "food_type", nullable = false)
    private String foodType;


}

