package com.joyfarm.farmstival.activity.repositories;

import com.joyfarm.farmstival.activity.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ActivityRepository  extends JpaRepository<Activity, Long>, QuerydslPredicateExecutor<Activity> {
}
