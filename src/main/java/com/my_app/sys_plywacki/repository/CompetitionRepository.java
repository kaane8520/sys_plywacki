package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long>{
    Competition findByIdCompetitions(Long along);
   // List<Competition> findByCompetitonName(String keyword);
    //List<Competition> search(String keyword);
}