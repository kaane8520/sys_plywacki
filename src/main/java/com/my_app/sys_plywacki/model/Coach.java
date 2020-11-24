package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoach;

    @OneToOne
    private Club club;

    public Coach(Club club) {
        this.club = club;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate coachlegidate;

    @OneToOne
    private Person person;

    public Coach() {

    }


    public Long getIdCoach() {
        return idCoach;
    }

    public void setIdCoach(Long idCoach) {
        this.idCoach = idCoach;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public LocalDate getCoachlegidate() {
        return coachlegidate;
    }

    public void setCoachlegidate(LocalDate coachlegidate) {
        this.coachlegidate = coachlegidate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
