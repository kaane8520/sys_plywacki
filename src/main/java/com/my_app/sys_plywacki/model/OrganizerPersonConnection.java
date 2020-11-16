package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class OrganizerPersonConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Organizer organizer;

    @ManyToOne
    Person person;

    public OrganizerPersonConnection(Organizer organizer, Person person) {
        this.organizer = organizer;
        this.person = person;
    }

    public OrganizerPersonConnection() {
    }
}
