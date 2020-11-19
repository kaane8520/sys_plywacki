package com.my_app.sys_plywacki.model;

import antlr.ASTNULLType;
import com.my_app.sys_plywacki.repository.PlayerPersonConnectionRepository;
import com.my_app.sys_plywacki.service.PlayerPersonConnectionService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
public class PlayerPersonConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Player player;
    @ManyToOne
    Person person;
//    @JoinColumn(name = "players")


    public PlayerPersonConnection(Player player, Person person) {
        this.player = player;
        this.person = person;
    }

    public PlayerPersonConnection() {

    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Person getPerson() {
    	return this.person;
    }

    public String getUsername() {

        return this.person.getUsername();
    }
}
