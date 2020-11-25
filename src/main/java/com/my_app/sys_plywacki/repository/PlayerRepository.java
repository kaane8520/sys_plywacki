package com.my_app.sys_plywacki.repository;


import com.my_app.sys_plywacki.model.Club;
import com.my_app.sys_plywacki.model.Message;
import com.my_app.sys_plywacki.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    List<Player> findAllByClub(Club club);
//    List<Player> findByIdPlayer(String keyword);
    @Nullable
    List<Player> findByIdPerson(Long idPerson);
    List<Player> findAllByPersonUsernameContains(String keyword);


    List<Player> findPlayersByClubCoachPersonUsernameContains(String keyword);
}