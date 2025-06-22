package com.gymapp.gym.persistence.dtos.Cls;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClassesDTO {

    private Long classID;

    private String name;

    private String timec;

    private Boolean dispo;

    private String descrip;

    private Integer maxCapacity;

    private Integer durationMinutes;

    private LocalDateTime createdAt;

    private Long coachId;

    private String coachName;

}
