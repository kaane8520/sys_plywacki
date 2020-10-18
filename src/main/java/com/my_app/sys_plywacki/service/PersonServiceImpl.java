package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Role;
import com.my_app.sys_plywacki.repository.RoleRepository;
import com.my_app.sys_plywacki.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Set;


@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(Person person) {
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        person.setRoles(new HashSet<>(roleRepository.findAll()));
        personRepository.save(person);
    }
    
    @Override
    public Person findByUsername(String email) {
        return personRepository.findByUsername(email);
    }
    @Override
    public void add_role(Person person, Role role) {
    	Set <Role>newRoles = person.getRoles();
    	newRoles.add(role);
    	person.setRoles(newRoles);
    }
}