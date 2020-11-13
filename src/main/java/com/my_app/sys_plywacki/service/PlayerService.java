package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PlayerService {



	public void save(Player player);
//   @Query("SELECT c FROM Club c WHERE c.clubName LIKE %?1%")
//    public List<Club> findByClubName(String clubName);

//    List<Club> findAll(String keyword);



    List<Player> findAll();
}