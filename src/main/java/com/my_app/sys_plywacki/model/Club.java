package com.my_app.sys_plywacki.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Club {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_club;
    private String club_name;


}
