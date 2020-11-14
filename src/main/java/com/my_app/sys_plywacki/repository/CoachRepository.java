package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Referee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Referee, Long> {
}
