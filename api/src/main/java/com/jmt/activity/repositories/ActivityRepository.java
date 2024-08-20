package com.jmt.activity.repositories;

import com.jmt.activity.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ActivityRepository extends JpaRepository<Activity, Long>, QuerydslPredicateExecutor<Activity> {
}
