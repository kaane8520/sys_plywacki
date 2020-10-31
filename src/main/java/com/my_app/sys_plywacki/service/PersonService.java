package com.my_app.sys_plywacki.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Role;

public interface PersonService {
    void save(Person person);

    Person findByUsername(String email);
    
    void add_role(Person person, Role role);
    
    Collection<? extends GrantedAuthority> getAuthorities(Role role);
    
    void update_user_role_if_exists();
}
