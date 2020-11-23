package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.ClubPlayerConnection;
import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.model.RefereePersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubPlayerConnectionRepository extends JpaRepository<ClubPlayerConnection, Long> {
    ClubPlayerConnection findByPlayer(Player player);
}
