package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Club;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long>{

//    List<Club> findByClubName(String keyword);
//    List<Club> findClubByClubname(String keyword);


//    @Query
    //    public List<Club> search(String keyword);


}