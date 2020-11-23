package com.my_app.sys_plywacki.model;

import javax.persistence.*;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getIdplayer(){
        return player.getIdPlayer();
    }

    public String getUsername() {
        return person.getUsername();
    }

    public String getClubname(){
        return player.getClubname();
    }
}
