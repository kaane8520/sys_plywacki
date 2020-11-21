package com.my_app.sys_plywacki.model;

import com.my_app.sys_plywacki.repository.ClubRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;

@Entity
public class Player{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate medExDate;

    private Long idClub;


    @OneToMany
    private List<ClubPlayerConnection> clubs;


    @OneToMany
    private List<PlayerPersonConnection> persons;

    @OneToOne
    private CategoriesOnCompetition categoriesOnCompetition;



    public Long getIdPlayer() {
        return idPlayer;
    }
    public Long getIdClub() {
        return idClub;
    }
    public void setIdClub(Long idClub) {
        this.idClub = idClub;
    }

    public List<ClubPlayerConnection> getClubs() {
        return clubs;
    }

    public void setClubs(List<ClubPlayerConnection> clubs) {
        this.clubs = clubs;
    }

    public List<PlayerPersonConnection> getPersons() {
        return persons;
    }

    public void setPersons(List<PlayerPersonConnection> persons) {
        this.persons = persons;
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
