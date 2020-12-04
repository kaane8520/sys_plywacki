package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Competition;
import com.my_app.sys_plywacki.model.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;

public interface CompetitionRepository extends JpaRepository<Competition, Long>{
    Competition findByIdCompetitions(Long along);
    boolean existsCompetitionByOrganizer(Organizer organizer);
    List<Competition> findCompetitionsByCompetitionDate(Date competitionDate);
    List<Competition> findCompetitionsByCompetitionNameContains(String keyword);
    List<Competition> findCompetitionsByOrganizer(Organizer organizer);
}