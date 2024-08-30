package com.joyfarm.farmstival.tour.repositories;

import com.joyfarm.farmstival.tour.entities.TourPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface TourPlaceRepository extends JpaRepository<TourPlace, Long>, QuerydslPredicateExecutor<TourPlace> {
}
