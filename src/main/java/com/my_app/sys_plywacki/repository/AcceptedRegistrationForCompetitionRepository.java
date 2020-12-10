package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.AcceptedRegistrationForCompetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface AcceptedRegistrationForCompetitionRepository extends JpaRepository<AcceptedRegistrationForCompetition, Long> {
    @Nullable
    List<AcceptedRegistrationForCompetition> findByIdCompetition(Long idCompetition);
}
