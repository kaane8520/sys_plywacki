package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Coach;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    @Override
    Optional<Coach> findById(Long aLong);
    Coach findCoachByPerson(Person person);
    Coach findCoachByIdCoach(Long aLong);
    @Nullable
    List<Coach> findByIdPerson(Long idPerson);
}
