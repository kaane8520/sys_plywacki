package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Organizer;
import com.my_app.sys_plywacki.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    Organizer findOrganizerByPerson(Person person);


}
