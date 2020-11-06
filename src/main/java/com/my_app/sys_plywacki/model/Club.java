package com.my_app.sys_plywacki.model;


import javax.persistence.*;

@Entity
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_club;
    private String clubname;


    @Override
    public String toString() {
        return "Club [id=" + id_club + ", club name=" + clubname +"]";
    }

    public Long getId_club() {
        return id_club;
    }

    public void setId_club(Long id_club) {
        this.id_club = id_club;
    }

    public String getClubname() {
        return clubname;
    }

    public void setClubname(String clubname) {
        this.clubname = clubname;
    }

//    List<Club> findAll(String keyword){
//        return null;
//    }
//    List<Club> findAll() {
//        return null;
//    }
}
