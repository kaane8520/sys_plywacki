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

    public RefereeRoleOnCompetition() {
    }

    public RefereeRoleOnCompetition(RefereeRoles refereeRoles) {
        this.refereeRoles = refereeRoles;
    }

    public RefereeRoleOnCompetition(Referee referee, Competition competition) {
        this.referee = referee;
        this.competition = competition;
    }
}
