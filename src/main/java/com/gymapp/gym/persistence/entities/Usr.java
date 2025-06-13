package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@Entity
@Data
public class Usr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String tel;

    private String dni;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usr_cls",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    private Set<Cls> classes= new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    /* @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

     */

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_type_id")
    private MemberType memberType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usr usr = (Usr) o;

        return Objects.equals(userID, usr.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }



}