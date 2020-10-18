package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Role;

public interface PersonService {
    void save(Person person);

    Person findByUsername(String email);
    
    void add_role(Person person, Role role);
}