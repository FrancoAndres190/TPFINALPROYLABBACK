package com.gymapp.gym.persistence.repository;

import com.gymapp.gym.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String rolName);

}