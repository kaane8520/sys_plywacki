package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long>{
    Competition findByIdCompetitions(Long along);

    List<Competition> findCompetitionsByCompetitionDate(Date competitionDate);
    List<Competition> findCompetitionsByCompetitionNameContains(String keyword);
}