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

    @OneToMany
    private List<RefereePersonConnection> persons;

    @OneToOne
    private RefereeRoleOnCompetition refereeRoleOnCompetition;

    public Long getIdReferee() {
        return idReferee;
    }

    public void setIdReferee(Long idReferee) {
        this.idReferee = idReferee;
    }

    public LocalDate getRefereelegidate() {
        return refereelegidate;
    }

    public void setRefereelegidate(LocalDate refereelegidate) {
        this.refereelegidate = refereelegidate;
    }
}
