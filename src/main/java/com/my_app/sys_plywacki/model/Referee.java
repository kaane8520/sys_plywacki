package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Referee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_referee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate refereeLegDate;

    public Long getId_referee() {
        return id_referee;
    }

    public void setId_referee(Long id_referee) {
        this.id_referee = id_referee;
    }

    public LocalDate getRefereeLegDate() {
        return refereeLegDate;
    }

    public void setRefereeLegDate(LocalDate refereeLegDate) {
        this.refereeLegDate = refereeLegDate;
    }
}
