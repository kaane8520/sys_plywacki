package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Club;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long>{
//    @Query(value ="Select c from Club c where c.clubname like %?!% ")
//    List<Club> findAllByClubname(String keyword);

//    List<Club> findByClubName(String keyword);
    List<Club> findClubByClubname(String keyword);






}