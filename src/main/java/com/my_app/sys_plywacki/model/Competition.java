package com.my_app.sys_plywacki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_competitions;
    private String competition_name;
    private LocalDate competition_date;


    public void search(String keyword){}


}
