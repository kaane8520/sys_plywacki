package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.OrganizerPersonConnection;
import com.my_app.sys_plywacki.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizerPersonConnectionRepository extends JpaRepository<OrganizerPersonConnection, Long> {
    OrganizerPersonConnection findByPerson(Person person);
}
