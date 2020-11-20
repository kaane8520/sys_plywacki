package com.my_app.sys_plywacki.model;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class ClubPlayerConnection {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Club club;

    @ManyToOne
    Player player;

    public ClubPlayerConnection(Club club, Player player) {
        this.club = club;
        this.player = player;
    }

    public ClubPlayerConnection() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getIdplayer(){
        return player.getIdPlayer();
    }

    public String getClubName(){
        return club.getClubname();
    }


}
