package com.my_app.sys_plywacki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Player extends User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_player;

    private LocalDate med_ex_date;

    public void search(String keyword){}
}
