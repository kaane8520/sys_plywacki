package com.my_app.sys_plywacki.model;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;

@Entity
public class ClubPlayerConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Club club;

    @ManyToOne
    Player player;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
