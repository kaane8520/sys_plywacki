package com.my_app.sys_plywacki.repository;


import com.my_app.sys_plywacki.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    List<Player> findAllByClub(Club club);
//    List<Player> findByIdPlayer(String keyword);
    List<Player> findPlayersByClubCoach(Coach coach);
    @Nullable
    List<Player> findByIdPerson(Long idPerson);
    List<Player> findAllByPersonUsernameContains(String keyword);

    Player findPlayerByPerson(Person person);
    List<Player> findPlayersByClubCoachPersonUsernameContains(String keyword);
    boolean existsByIdPerson(Long aLong);
}