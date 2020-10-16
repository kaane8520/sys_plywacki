package com.my_app.sys_plywacki.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

public class Result {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_result;
}
