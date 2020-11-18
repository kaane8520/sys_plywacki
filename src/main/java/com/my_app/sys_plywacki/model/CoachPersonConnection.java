package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class CoachPersonConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Coach coach;

    @ManyToOne
    Person person;

    public CoachPersonConnection(Coach coach, Person person) {
        this.coach = coach;
        this.person = person;
    }

    public CoachPersonConnection() {
    }
}
