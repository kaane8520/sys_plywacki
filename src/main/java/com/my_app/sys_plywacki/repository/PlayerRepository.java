package com.my_app.sys_plywacki.repository;


import com.my_app.sys_plywacki.model.Club;
import com.my_app.sys_plywacki.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    List<Player> findAllByClub(Club club);
//    List<Player> findByIdPlayer(String keyword);
}