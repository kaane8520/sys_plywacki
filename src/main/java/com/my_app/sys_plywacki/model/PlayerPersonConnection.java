package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class PlayerPersonConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Player player;
    @ManyToOne
    Person person;
//    @JoinColumn(name = "players")


    public PlayerPersonConnection(Player player, Person person) {
        this.player = player;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Person getPerson() {
    	return this.person;
    }
}
