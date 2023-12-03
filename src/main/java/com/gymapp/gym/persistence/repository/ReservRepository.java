package com.gymapp.gym.persistence.repository;

import com.gymapp.gym.persistence.entities.Reserv;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservRepository  extends JpaRepository<Reserv, Long> {
}
