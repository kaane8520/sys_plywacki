package com.my_app.sys_plywacki.model;

import com.my_app.sys_plywacki.repository.ClubRepository;
import com.my_app.sys_plywacki.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.my_app.sys_plywacki.repository.PersonRepository;

@Entity
public class Player{

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate medExDate;
    @ManyToOne
    private Club club;

    @OneToOne
    private Person person;

    @ManyToOne
    private CategoriesOnCompetition categoriesOnCompetition;

    //@Autowired
    //private PersonRepository personRepository;

    private Long idPerson;
    public Player() {
    }

    /*public String searchForUsername(){
        Optional<Person> p = personRepository.findById(idPerson);
        return p.get().getUsername();
    }*/
    public Player(Club club) {
        this.club = club;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public Long getIdPerson() {return idPerson; }
    public void setIdPerson(Long idPerson) {this.idPerson = idPerson; }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public CategoriesOnCompetition getCategoriesOnCompetition() {
        return categoriesOnCompetition;
    }

    public void setCategoriesOnCompetition(CategoriesOnCompetition categoriesOnCompetition) {
        this.categoriesOnCompetition = categoriesOnCompetition;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public String getClubname(){
        return club.getClubname();
    }

    public Long getIdClub(){
        return club.getId_club();
    }

    public String getUsername(){
        return person.getUsername();
    }

}
