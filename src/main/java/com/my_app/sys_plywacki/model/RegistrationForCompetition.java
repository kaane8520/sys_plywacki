package com.my_app.sys_plywacki.model;

import com.my_app.sys_plywacki.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Entity
public class RegistrationForCompetition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCoach;
    private Long idClub;
    private Long idCompetition;

    private String clubName;
    private String coachName;
    private String categoryName;

    private Long idCategories;
    @ManyToMany
    @Column
    private List<Player> players;


    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public Long getIdCategories(){
        return idCategories;
    }
    public void setIdCategories(Long idCategories){
        this.idCategories = idCategories;
    }

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
    public void setClubName(String clubName){
        this.clubName = clubName;
    }
    public String getClubName(){
        return this.clubName;
    }
    public void setCoachName(String coachName){
        this.coachName = coachName;
    }
    public String getCoachName(){
        return this.coachName;
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }
    public String getCategoryName(){
        return this.categoryName;
    }
}
