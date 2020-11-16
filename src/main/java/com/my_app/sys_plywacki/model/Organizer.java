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
}
