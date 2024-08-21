package com.jmt.restaurant.repositories;

import com.jmt.restaurant.entities.RestaurantImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RestaurantImageRepository extends JpaRepository<RestaurantImage, Long>, QuerydslPredicateExecutor<RestaurantImage> {
}
