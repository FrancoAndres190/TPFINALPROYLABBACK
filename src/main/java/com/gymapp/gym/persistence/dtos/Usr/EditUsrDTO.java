package com.gymapp.gym.persistence.dtos.Usr;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class EditUsrDTO {

    private String userEmail;

    private String oldPassword;

    private String firstName;

    private String lastName;

    private String password;

    private String tel;

    private String dni;

}
