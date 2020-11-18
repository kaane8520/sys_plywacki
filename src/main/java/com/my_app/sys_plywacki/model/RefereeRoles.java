package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class RefereeRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refereerolename;

    @OneToOne
    private RefereeRoleOnCompetition refereeRoleOnCompetition;

    public RefereeRoles() {
    }

    public RefereeRoles(RefereeRoleOnCompetition refereeRoleOnCompetition) {
        this.refereeRoleOnCompetition = refereeRoleOnCompetition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefereerolename() {
        return refereerolename;
    }

    public void setRefereerolename(String refereerolename) {
        this.refereerolename = refereerolename;
    }

    public RefereeRoleOnCompetition getRefereeRoleOnCompetition() {
        return refereeRoleOnCompetition;
    }

    public void setRefereeRoleOnCompetition(RefereeRoleOnCompetition refereeRoleOnCompetition) {
        this.refereeRoleOnCompetition = refereeRoleOnCompetition;
    }
}
