package com.my_app.sys_plywacki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_club;
    @Column(nullable = false)
    private String club_name;
    
    @OneToMany
    @Column 
    private Set<ClubPlayerConnection> players;

    @Override
    public String toString() {
        return "Club [id=" + id_club + ", club name=" + club_name +"]";
    }

    public Long getId_club() {
        return id_club;
    }

    public void setId_club(Long id_club) {
        this.id_club = id_club;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public Set<ClubPlayerConnection> getPlayers() {
        return players;
    }

    public void setPlayers(Set<ClubPlayerConnection> players) {
        this.players = players;
    }
}
