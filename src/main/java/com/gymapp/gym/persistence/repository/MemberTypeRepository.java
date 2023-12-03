package com.gymapp.gym.persistence.repository;

import com.gymapp.gym.persistence.entities.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberTypeRepository extends JpaRepository<MemberType, Long> {
}