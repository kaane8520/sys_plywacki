package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_result;

    @OneToMany
    private List<CategoriesOnCompetition> categoriesOnCompetition;
    @ManyToOne
    private Competition competition;
    @OneToOne
    private Disqualification disqualification;

    private Time timeOfresult;

    public Long getId_result() {
        return id_result;
    }

    public void setId_result(Long id_result) {
        this.id_result = id_result;
    }

    public List<CategoriesOnCompetition> getCategoriesOnCompetition() {
        return categoriesOnCompetition;
    }

    public void setCategoriesOnCompetition(List<CategoriesOnCompetition> categoriesOnCompetition) {
        this.categoriesOnCompetition = categoriesOnCompetition;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Disqualification getDisqualification() {
        return disqualification;
    }

    public void setDisqualification(Disqualification disqualification) {
        this.disqualification = disqualification;
    }

    public Time getTimeOfresult() {
        return timeOfresult;
    }

    public void setTimeOfresult(Time timeOfresult) {
        this.timeOfresult = timeOfresult;
    }
}
