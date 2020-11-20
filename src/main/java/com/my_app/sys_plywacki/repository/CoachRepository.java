package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Coach;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    @Override
    Optional<Coach> findById(Long aLong);
}
