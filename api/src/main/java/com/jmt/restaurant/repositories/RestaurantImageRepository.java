package com.jmt.restaurant.repositories;

import com.jmt.restaurant.entities.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long> {
}
