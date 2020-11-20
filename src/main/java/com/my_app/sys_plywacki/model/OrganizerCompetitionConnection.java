package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class OrganizerCompetitionConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Competition competition;

    @ManyToOne
    Organizer organizer;

    public OrganizerCompetitionConnection(Competition competition, Organizer organizer) {
        this.competition = competition;
        this.organizer = organizer;
    }

    public OrganizerCompetitionConnection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    public void getIdorganizer(){
        this.organizer.getId_organizer();
    }
}
