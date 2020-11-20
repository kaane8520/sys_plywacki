package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.CoachPersonConnection;
import com.my_app.sys_plywacki.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachPersonConnectionRepository extends JpaRepository<CoachPersonConnection, Long> {
    CoachPersonConnection findByPerson(Person person);
}
