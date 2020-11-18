package com.my_app.sys_plywacki.model;



import javax.persistence.*;

@Entity
public class RefereePersonConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Referee referee;

    @ManyToOne
    Person person;

    public RefereePersonConnection(Referee referee, Person person) {
        this.referee = referee;
        this.person = person;
    }

    public RefereePersonConnection() {
    }
}
