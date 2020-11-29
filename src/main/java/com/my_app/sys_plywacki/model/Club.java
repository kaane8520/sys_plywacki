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
    @Column
    private String clubname;
    
    @OneToMany
    @Column
    private List<Player> players;

    @OneToOne
    private Coach coach;
    private Long idCoach;

    @ManyToOne
    private Competition competition;


//    public Club(Coach coach) {
//        this.coach = coach;
//    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }



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

    public Club() {
    }

    public Club(Coach coach) {
        this.coach = coach;
    }

    public Long getId_coach(){
        return coach.getIdCoach();
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Long getIdCompetition(){
        return competition.getIdCompetitions();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
