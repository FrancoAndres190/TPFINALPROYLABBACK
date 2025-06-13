package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleID;

    private String name;

    //@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    //private Set<Usr> users = new HashSet<>();

    /* @OneToMany(mappedBy = "role")
    private Set<Usr> users;

     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return Objects.equals(roleID, role.roleID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleID);
    }

}
