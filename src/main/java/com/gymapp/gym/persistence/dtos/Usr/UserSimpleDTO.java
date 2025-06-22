package com.gymapp.gym.persistence.dtos.Usr;

import lombok.Data;

@Data
public class UserSimpleDTO {
    private Long userID;
    private String firstName;
    private String lastName;
    private String email;
}
