package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.model.PlayerPersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerPersonConnectionRepository extends JpaRepository<PlayerPersonConnection, Long> {

    PlayerPersonConnection findByPerson(Person person);
}
