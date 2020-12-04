package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.RegistrationForCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationForCompetitionRepository extends JpaRepository<RegistrationForCompetition, Long> {
}
