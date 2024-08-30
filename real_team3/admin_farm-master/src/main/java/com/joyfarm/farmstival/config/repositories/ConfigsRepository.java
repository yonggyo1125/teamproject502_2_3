package com.joyfarm.farmstival.config.repositories;


import com.joyfarm.farmstival.config.entities.Configs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigsRepository extends JpaRepository<Configs, String> {
}
