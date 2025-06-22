package com.gymapp.gym.persistence.repository;

import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClsRepository extends JpaRepository<Cls, Long> {

    List<Cls> findByCoach(Usr coach);
    List<Cls> findByCoachOrderByCreatedAtDesc(Usr coach);
    List<Cls> findAllByOrderByCreatedAtDesc();



}
