package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CompetitionService extends JpaRepository<Competition, Long> {

    @Query("SELECT c FROM Competition c WHERE c.competition_name LIKE %?1%")
    public List<Competition> search (String keyword);

    List<Competition> listAll(String keyword);

    List<Competition> listAll();
}