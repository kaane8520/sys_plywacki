package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class CategoriesOnCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Categories categories;

    @ManyToOne
    private Competition competition;

    @ManyToOne
    private Result result;
    @OneToOne
    private Player player;

    public CategoriesOnCompetition(Player player) {
        this.player = player;
    }

    public CategoriesOnCompetition() {

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
