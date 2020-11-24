package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefereeRepository extends JpaRepository<Referee, Long> {
    Referee findRefereeByPerson(Person person);
//    void saveReferee(Referee referee);
}
