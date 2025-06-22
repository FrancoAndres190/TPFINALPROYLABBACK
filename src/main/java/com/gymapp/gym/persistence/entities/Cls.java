package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
public class Cls {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long classID;

    private String name;

    private String timec;

    private Boolean dispo;

    private String descrip;

    private Integer maxCapacity;

    private Integer durationMinutes;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany(mappedBy = "classes", fetch = FetchType.EAGER)
    private Set<Usr> users = new HashSet<>();

    // Coach que creó la clase
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Usr coach;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cls cls = (Cls) o;

        return Objects.equals(classID, cls.classID);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID);
    }

}