package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ClubSearchService extends JpaRepository<Club, Long> {

    @Query("SELECT p FROM Club p WHERE p.club_name LIKE %?1$")
    public List<Club> search (String keyword);

    List<Club> listAll(String keyword);
}