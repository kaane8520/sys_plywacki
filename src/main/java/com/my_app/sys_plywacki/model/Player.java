package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Player{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate medExDate;
//
    private Long idClub;

    private Long idPerson;

    @ManyToMany
    Set<Club> clubs;


    @OneToMany
    List<PlayerPersonConnection> persons;

//    public Set<Person> getPersons() {
//        return persons;
//    }
//
//    public void setPersons(Set<Person> persons) {
//        this.persons = persons;
//    }

    public Long getIdPlayer() {
        return idPlayer;
    }
    public Long getIdClub() {
        return idClub;
    }
    public void setIdClub(Long idClub) {
        this.idClub = idClub;
    }


    public Set<Club> getClubs() {
        return clubs;
    }

    public void setClubs(Set<Club> clubs) {
        this.clubs = clubs;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }
    
    public LocalDate getMedExDate() {
        return medExDate;
    }

    public void setMedExDate(LocalDate medExDate) {
        this.medExDate = medExDate;
    }
    public void search(String keyword){}
}
