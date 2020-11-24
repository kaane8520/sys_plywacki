package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.List;
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_result;

    @OneToMany
    private List<CategoriesOnCompetition> categoriesOnCompetition;
    @ManyToOne
    private Competition competition;
}
