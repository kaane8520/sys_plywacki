package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class CategoriesOnCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Categories categories;

    @ManyToOne
    private Competition competition;

    @OneToOne
    private Result result;
    @OneToMany
    private List<Player> players;

    public CategoriesOnCompetition() {
    }

    public CategoriesOnCompetition(Competition competition, List<Player> players) {
        this.competition = competition;
        this.players = players;
    }

    public CategoriesOnCompetition(Competition competition) {
        this.competition = competition;
    }

    public CategoriesOnCompetition(List<Player> players) {
        this.players = players;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getPlayerById(Long along){
        return players.get(Math.toIntExact(along)).getUsername();
    }

    public String getCategoriesName(){
        return categories.getNamecategory();
    }

    public String gettimeofresult(){
        return result.getTimeofresult();
    }

    public String getcompetitionname(){
        return competition.getCompetitionName();
    }

    public String getdisqualificationname(){
        return result.getdisqualificationname();
    }
}
