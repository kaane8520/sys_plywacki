package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Guest extends Person {
    //@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_guest;

    @ManyToMany
    private Set<Role> roles;

    /*public String displayScore() {}

    public String displayClubs() {}

    public String displayPlayers() {}

    public String displayEvents() {} */
}
