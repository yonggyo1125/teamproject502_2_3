package com.joyfarm.farmstival.activity.repositories;

import com.joyfarm.farmstival.activity.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, QuerydslPredicateExecutor<Reservation> {
}
