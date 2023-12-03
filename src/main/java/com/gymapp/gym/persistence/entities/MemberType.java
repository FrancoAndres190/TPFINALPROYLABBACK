package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class MemberType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer price;
    @Column
    private Integer daysof;
    @Column
    private String descipt;

    //Una membresia para muchos usuarios
    @OneToMany(mappedBy = "memberType", cascade = CascadeType.ALL)
    private List<Usr> usrs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDaysof() {
        return daysof;
    }

    public void setDaysof(Integer daysof) {
        this.daysof = daysof;
    }

    public String getDescipt() {
        return descipt;
    }

    public void setDescipt(String descipt) {
        this.descipt = descipt;
    }
}