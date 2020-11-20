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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getIdperson(){
        return person.getId_person();
    }
    public Long getIdorganizer(){
        return organizer.getId_organizer();
    }
}
