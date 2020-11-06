package com.my_app.sys_plywacki.model;

import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
public class Player extends User {
	//@Id dlacego to nie dziala?
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPlayer;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate medExDate;

    //private Long id_club;

    public Long getIdPlayer() {
        return idPlayer;
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
