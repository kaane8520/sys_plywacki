package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Person;

public interface PersonService {
    void save(Person person);

    Person findByUsername(String email);
}