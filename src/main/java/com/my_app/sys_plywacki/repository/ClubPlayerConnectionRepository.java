package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubPlayerConnectionRepository extends JpaRepository<ClubPlayerConnection, Long> {
    ClubPlayerConnection findByPlayer(Player player);
    List<ClubPlayerConnection> findAllByClub(Club club);
}
