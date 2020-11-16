package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String email);
}