package com.gymapp.gym.persistence.dtos.Usr;

import com.gymapp.gym.persistence.entities.Cls;
import com.gymapp.gym.persistence.entities.MemberType;
import com.gymapp.gym.persistence.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class GetUsrDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String tel;
    private String dni;
    private Set<Role> roles;
    private MemberType memberType;

}
