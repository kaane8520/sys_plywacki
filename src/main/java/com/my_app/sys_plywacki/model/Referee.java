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
    private Long idReferee;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate refereelegidate;
}
