package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class RefereeRoleOnCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private RefereeRoles refereeRoles;

    @ManyToOne
    private Referee referee;

    @ManyToOne
    Competition competition;
}
