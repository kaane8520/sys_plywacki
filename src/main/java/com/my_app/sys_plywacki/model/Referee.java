package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Referee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReferee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate refereelegidate;

    @OneToOne
    private Person person;

    private Long idPerson;

    @OneToOne
    private RefereeRoleOnCompetition refereeRoleOnCompetition;

    public Long getIdReferee() {
        return idReferee;
    }

    public void setIdReferee(Long idReferee) {
        this.idReferee = idReferee;
    }

    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public LocalDate getRefereelegidate() {
        return refereelegidate;
    }

    public void setRefereelegidate(LocalDate refereelegidate) {
        this.refereelegidate = refereelegidate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public RefereeRoleOnCompetition getRefereeRoleOnCompetition() {
        return refereeRoleOnCompetition;
    }

    public void setRefereeRoleOnCompetition(RefereeRoleOnCompetition refereeRoleOnCompetition) {
        this.refereeRoleOnCompetition = refereeRoleOnCompetition;
    }
}
