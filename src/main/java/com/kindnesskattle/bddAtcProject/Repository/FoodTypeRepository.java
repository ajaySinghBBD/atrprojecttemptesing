package com.kindnesskattle.bddAtcProject.Repository;

import com.kindnesskattle.bddAtcProject.Entities.FoodType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {
}
