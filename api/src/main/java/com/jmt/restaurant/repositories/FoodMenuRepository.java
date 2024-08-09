package com.jmt.restaurant.repositories;

import com.jmt.restaurant.entities.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Long>, QuerydslPredicateExecutor<FoodMenu> {
}
