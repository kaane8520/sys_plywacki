package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;

    private String name;

    @ManyToOne
    private Person person;


    public Long getId() {
        return id_role;
    }

    public void setId(Long id) {
        this.id_role = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}