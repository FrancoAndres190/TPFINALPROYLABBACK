package com.gymapp.gym.persistence.dtos.Usr;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddMemberDTO {

    private long userID;
    private long memberTypeID;

}