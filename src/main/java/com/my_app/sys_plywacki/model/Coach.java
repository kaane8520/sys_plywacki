package com.my_app.sys_plywacki.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Coach extends User{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_coach;
    private Club id_club;


}
