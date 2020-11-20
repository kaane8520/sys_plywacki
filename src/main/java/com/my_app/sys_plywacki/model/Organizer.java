package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organizer{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_organizer;

    @OneToMany
    @Column
    private List<OrganizerCompetitionConnection> competitions;

    @OneToMany
    @Column
    private List<OrganizerPersonConnection> persons;

    public Long getId_organizer() {
        return id_organizer;
    }

    public void setId_organizer(Long id_organizer) {
        this.id_organizer = id_organizer;
    }

    public List<OrganizerCompetitionConnection> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<OrganizerCompetitionConnection> competitions) {
        this.competitions = competitions;
    }

    public List<OrganizerPersonConnection> getPersons() {
        return persons;
    }

    public void setPersons(List<OrganizerPersonConnection> persons) {
        this.persons = persons;
    }
}
