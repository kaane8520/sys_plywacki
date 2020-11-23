package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByUsername(String email);
    Person findByPlayer(Player player);
    Person findByCoach(Coach coach);
}