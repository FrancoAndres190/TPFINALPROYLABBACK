package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class MemberType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberTypeID;

    private String name;

    private Integer price;

    private Integer daysof;

    private String descipt;

    @OneToMany(mappedBy = "memberType", fetch = FetchType.EAGER)
    private Set<Usr> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberType memberType = (MemberType) o;

        return Objects.equals(memberTypeID, memberType.memberTypeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberTypeID);
    }

}