package com.my_app.sys_plywacki.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;

public class Player {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_player;

    private LocalDate med_ex_date;
}
