package com.gymapp.gym.persistence.repository;

import com.gymapp.gym.persistence.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Rol, Long> {

    Rol findByRolName(String rolName);

}