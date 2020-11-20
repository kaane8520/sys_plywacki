package com.my_app.sys_plywacki.repository;


import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.RefereePersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefereePersonConnectionRepository extends JpaRepository<RefereePersonConnection, Long> {
    RefereePersonConnection findByPerson(Person person);

}
