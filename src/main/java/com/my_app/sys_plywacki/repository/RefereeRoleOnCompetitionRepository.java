package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.RefereeRoleOnCompetition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefereeRoleOnCompetitionRepository extends JpaRepository<RefereeRoleOnCompetition, Long> {
    @Override
    Optional<RefereeRoleOnCompetition> findById(Long aLong);
}
