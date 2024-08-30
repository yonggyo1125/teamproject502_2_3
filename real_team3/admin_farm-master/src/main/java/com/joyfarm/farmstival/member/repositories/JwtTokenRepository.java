package com.joyfarm.farmstival.member.repositories;

import com.joyfarm.farmstival.member.entities.JwtToken;
import org.springframework.data.repository.CrudRepository;

public interface JwtTokenRepository extends CrudRepository<JwtToken, String> {
}
