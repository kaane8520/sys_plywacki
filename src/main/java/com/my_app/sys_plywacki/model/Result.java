package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idresult;

    @OneToMany
    private List<CategoriesOnCompetition> categoriesOnCompetition;
    @ManyToOne
    private Competition competition;
    @OneToOne
    private Disqualification disqualification;

    private Time timeofresult;

    public Long getIdresult() {
        return idresult;
    }

    public void setId_result(Long id_result) {
        this.idresult = idresult;
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

    public Time getTimeofresult() {
        return timeofresult;
    }

    public void setTimeofresult(Time timeOfresult) {
        this.timeofresult = timeOfresult;
    }
}
