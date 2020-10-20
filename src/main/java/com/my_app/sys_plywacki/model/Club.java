package com.my_app.sys_plywacki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClub;
    private String clubName;



    public void findByClubName(String keyword){

    }
//    List<Club> findAll(String keyword){
//        return null;
//    }
//    List<Club> findAll() {
//        return null;
//    }
}
