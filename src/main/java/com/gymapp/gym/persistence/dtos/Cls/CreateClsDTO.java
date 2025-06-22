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

        private Integer maxCapacity;

        private Integer durationMinutes;
    }

