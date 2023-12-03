package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Cls {

    //Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column
    private String name;
    @Column
    private String timec;
    @Column
    private Boolean dispo;
    @Column
    private String descrip;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usr_id", referencedColumnName = "id")
    private Usr usr;*/

    @ManyToMany(mappedBy = "clss")
    private Set<Usr> usrs;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimec() {
        return timec;
    }

    public void setTimec(String timec) {
        this.timec = timec;
    }

    public Boolean getDispo() {
        return dispo;
    }

    public void setDispo(Boolean dispo) {
        this.dispo = dispo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

   /* public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }*/
}