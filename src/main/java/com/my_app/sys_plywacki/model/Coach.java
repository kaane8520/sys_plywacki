package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCoach;

    @OneToOne
    Coach coach;

    @OneToOne
    Club club;

    public Coach(Coach coach, Club club) {
        this.coach = coach;
        this.club = club;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate coachlegidate;

    public Coach() {

    }
}
