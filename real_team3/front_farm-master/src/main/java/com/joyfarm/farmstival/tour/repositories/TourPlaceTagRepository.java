package com.joyfarm.farmstival.tour.repositories;

import com.joyfarm.farmstival.tour.entities.TourPlaceTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TourPlaceTagRepository extends JpaRepository<TourPlaceTag, String>, QuerydslPredicateExecutor<TourPlaceTag> {
}
