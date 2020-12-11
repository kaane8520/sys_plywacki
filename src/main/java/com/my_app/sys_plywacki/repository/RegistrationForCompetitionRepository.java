package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.RegistrationForCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationForCompetitionRepository extends JpaRepository<RegistrationForCompetition, Long> {
    @Nullable
    List<RegistrationForCompetition> findByIdCompetition(Long idCompetition);
}
