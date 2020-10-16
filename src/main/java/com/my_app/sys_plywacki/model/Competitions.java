package com.my_app.sys_plywacki.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

public class Competitions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_competitions;
    private String competiton_name;
    private LocalDate competition_date;

}
