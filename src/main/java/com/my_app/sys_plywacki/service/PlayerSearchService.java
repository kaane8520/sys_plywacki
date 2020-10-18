package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PlayerSearchService extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.username LIKE %?1$")
    public List<Player> search (String keyword);

    List<Player> listAll(String keyword);
}