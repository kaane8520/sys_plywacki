package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Competition;
import com.my_app.sys_plywacki.model.Referee;
import com.my_app.sys_plywacki.model.RefereeRoleOnCompetition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefereeRoleOnCompetitionRepository extends JpaRepository<RefereeRoleOnCompetition, Long> {
    @Override
    Optional<RefereeRoleOnCompetition> findById(Long aLong);

    List<RefereeRoleOnCompetition> findRefereeRoleOnCompetitionByCompetition(Competition competition);
}
