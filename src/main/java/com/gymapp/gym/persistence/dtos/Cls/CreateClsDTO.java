package com.gymapp.gym.persistence.dtos.Cls;

import com.gymapp.gym.persistence.entities.Usr;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateClsDTO {

    private String name;

    private String timec;

    private Boolean dispo;

    private String descrip;

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        CreateClsDTO other = (CreateClsDTO) obj;
//        return name == other.name;
//    }

}
