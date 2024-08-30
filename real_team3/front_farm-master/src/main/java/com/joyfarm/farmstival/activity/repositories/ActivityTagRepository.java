package com.joyfarm.farmstival.activity.repositories;

import com.joyfarm.farmstival.activity.entities.ActivityTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ActivityTagRepository extends JpaRepository<ActivityTag, String>, QuerydslPredicateExecutor<ActivityTag> {
}
