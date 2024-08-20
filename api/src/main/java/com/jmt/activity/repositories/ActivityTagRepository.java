package com.jmt.activity.repositories;

import com.jmt.activity.entities.ActivityTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityTagRepository extends JpaRepository<ActivityTag, String> {
}