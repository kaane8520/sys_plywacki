package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Club;
import com.my_app.sys_plywacki.model.Coach;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long>{
//    @Query(value ="Select c from Club c where c.clubname like %?!% ")

    Club findClubByCoach(Coach coach);
    List<Club> findClubsByClubnameContains(String keyword);
    boolean existsClubByCoach(Coach coach);
//    List<Club> findClubByClubname(String keyword);






}