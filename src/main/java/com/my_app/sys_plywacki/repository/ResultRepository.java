package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    Result findResultByIdresult(Long along);
    List<Result> findResultsByCompetition_IdCompetitions(Long along);

}
