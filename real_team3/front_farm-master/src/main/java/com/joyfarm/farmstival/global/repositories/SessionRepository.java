package com.joyfarm.farmstival.global.repositories;

import com.joyfarm.farmstival.global.entities.SessionEntity;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<SessionEntity, String> {
}
