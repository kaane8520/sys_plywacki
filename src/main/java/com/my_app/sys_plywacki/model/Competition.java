package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCompetitions;
    private String competitionName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate competitionDate;

    @OneToMany
    @Column
    private List<OrganizerCompetitionConnection> organizers;

    public Long getIdCompetitions() {
        return idCompetitions;
    }

    public void setIdCompetitions(Long idCompetitions) {
        this.idCompetitions = idCompetitions;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public LocalDate getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(LocalDate competitionDate) {
        this.competitionDate = competitionDate;
    }

    public void search(String keyword){}

}
