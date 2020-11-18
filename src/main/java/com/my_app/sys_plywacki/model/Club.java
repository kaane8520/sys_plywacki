package com.my_app.sys_plywacki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.*;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_club;
    @Column(nullable = false)
    private String clubname;
    
    @OneToMany
    @Column 
    private List<ClubPlayerConnection> players;

    @Override
    public String toString() {
        return "Club [id=" + id_club + ", clubname=" + clubname +"]";
    }

    public Long getId_club() {
        return id_club;
    }

    public void setId_club(Long id_club) {
        this.id_club = id_club;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

    public List<ClubPlayerConnection> getPlayers() {
        return players;
    }

    public void setPlayers(List<ClubPlayerConnection> players) {
        this.players = players;
    }
}
