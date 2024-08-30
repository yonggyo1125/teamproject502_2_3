package com.joyfarm.farmstival.farmfarm.repositories;

import com.joyfarm.farmstival.farmfarm.entities.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FestivalRepository extends JpaRepository<Festival, Long>, QuerydslPredicateExecutor<Festival> {
}
