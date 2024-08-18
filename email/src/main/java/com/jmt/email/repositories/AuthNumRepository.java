package com.jmt.email.repositories;

import com.jmt.email.entities.AuthNum;
import org.springframework.data.repository.CrudRepository;

public interface AuthNumRepository extends CrudRepository<AuthNum, String> {
}
