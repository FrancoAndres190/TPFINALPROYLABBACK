package com.gymapp.gym.persistence.entities;

import jakarta.persistence.*;

@Entity
public class Reserv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
/*    @Id
    @OneToOne
    @JoinColumn(name = "usr_id", nullable = false, referencedColumnName = "id")
    private Usr usr;

    @Id
    @OneToOne
    @JoinColumn(name = "cls_id", nullable = false, referencedColumnName = "id")
    private Cls cls;

    public Usr getUsr() {
        return usr;
    }

    public void setUsr(Usr usr) {
        this.usr = usr;
    }

    public Cls getCls() {
        return cls;
    }

    public void setCls(Cls cls) {
        this.cls = cls;
    }*/



}
