package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class RegistrationForCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCoach;
    private Long idClub;
    private Long idCompetition;
    @ManyToMany
    @Column
    private List<Player> players;

    public Long getIdCoach(){
        return idCoach;
    }
    public void setIdCoach(Long idCoach){
        this.idCoach = idCoach;
    }

    public Long getIdCompetition(){
        return idCompetition;
    }
    public void setIdCompetition(Long idCompetition){
        this.idCompetition = idCompetition;
    }

    public Long getIdClub(){
        return idClub;
    }
    public void setIdClub(Long idClub){
        this.idClub = idClub;
    }
    public Long getIdRegistrationForCompetition(){
        return id;
    }
    public void setPlayers(List<Player> players){
        this.players = players;
    }
    public List<Player> getPlayers(){
        return this.players;
    }
    public void setIdRegistrationForCompetition(Long idRegistrationForCompetition){
        this.id = idRegistrationForCompetition;
    }
}
