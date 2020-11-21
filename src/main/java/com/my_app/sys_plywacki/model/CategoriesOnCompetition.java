package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class CategoriesOnCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Categories categories;

    @ManyToOne
    private Competition competition;

    @ManyToOne
    private Result result;
    @OneToOne
    private Player player;
}
