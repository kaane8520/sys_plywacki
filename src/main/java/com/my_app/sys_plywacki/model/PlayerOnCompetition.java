/*package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class PlayerOnCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayerOnCompetition;

    @OneToMany
    private List<Player> players;

    @ManyToOne
    private Competition competition;

    public PlayerOnCompetition() {
    }

    public PlayerOnCompetition(List<Player> players, Competition competition) {
        this.players = players;
        this.competition = competition;
    }

    public PlayerOnCompetition(Competition competition) {
        this.competition = competition;
    }

    public PlayerOnCompetition(List<Player> players) {
        this.players = players;
    }

    public Long getIdPlayerOnCompetition() {
        return idPlayerOnCompetition;
    }

    public void setIdPlayerOnCompetition(Long idPlayerOnCompetition) {
        this.idPlayerOnCompetition = idPlayerOnCompetition;
    }

    public List<Player> getPlayer() {
        return players;
    }

    public void setPlayer(List<Player> players) {
        this.players = players;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}*/
