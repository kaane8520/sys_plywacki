package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface RefereeRepository extends JpaRepository<Referee, Long> {
    Referee findRefereeByPerson(Person person);
//    void saveReferee(Referee referee);
    @Nullable
    List<Referee> findByIdPerson(Long idPerson);
}
