package com.my_app.sys_plywacki.model;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class ClubPlayerConnection {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Club club;

    @ManyToOne
    Player player;

    public ClubPlayerConnection(Club club, Player player) {
        this.club = club;
        this.player = player;
    }

    public ClubPlayerConnection() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
