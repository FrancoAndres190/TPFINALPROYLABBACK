package com.gymapp.gym.persistence.dtos.Usr;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PayMembershipDTO {

    private Long userId;
    private LocalDate membershipPaidUntil;
}
