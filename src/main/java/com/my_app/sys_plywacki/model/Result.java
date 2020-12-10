package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idresult;

    @OneToOne
    private CategoriesOnCompetition categoriesOnCompetition;
    @ManyToOne
    private Competition competition;
    @OneToOne
    private Disqualification disqualification;

    private String timeofresult;

    public Long getIdresult() {
        return idresult;
    }

    public void setIdresult(Long idresult) {
        this.idresult = idresult;
    }

    public CategoriesOnCompetition getCategoriesOnCompetition() {
        return categoriesOnCompetition;
    }

    public void setCategoriesOnCompetition(CategoriesOnCompetition categoriesOnCompetition) {
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

    public String getTimeofresult() {
        return timeofresult;
    }

    public void setTimeofresult(String timeOfresult) {
        this.timeofresult = timeOfresult;
    }

    public String getdisqualificationname(){
        return disqualification.getDisqualificationname();
    }

    public String getcategoryname(){
        return categoriesOnCompetition.getCategoriesName();
    }

    public String getcompetitionname(){
        return competition.getCompetitionName();
    }
}
