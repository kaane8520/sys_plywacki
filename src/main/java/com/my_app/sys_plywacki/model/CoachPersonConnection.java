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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    public Long getIdcoach(){

        return coach.getIdCoach();
    }
    public Long getIdperson(){
        return person.getIdPerson();
    }


}
