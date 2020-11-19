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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefereeRoles getRefereeRoles() {
        return refereeRoles;
    }

    public void setRefereeRoles(RefereeRoles refereeRoles) {
        this.refereeRoles = refereeRoles;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

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
