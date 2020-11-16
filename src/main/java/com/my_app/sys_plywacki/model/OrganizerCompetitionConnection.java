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
}
