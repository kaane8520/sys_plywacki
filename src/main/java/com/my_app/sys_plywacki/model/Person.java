package com.my_app.sys_plywacki.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
public class Person {
    @NotNull private BigDecimal price;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    @Column(nullable = false)
    private String username;
//    @Column
//    private String firstName;
//    @Column
//    private String lastName;
    @Column(nullable = false)
    private String password;



    @Transient
    @Column //(name = "passwordConfirm")
    private String passwordConfirm;

    @ManyToMany
    @Column //(name = "roles")
    private Set<Role> roles;

    //role użytkowników
    @OneToOne
    private Player player;

    @OneToOne
    private Organizer organizer;

    @OneToOne
    private Coach coach;

    @OneToOne
    private Referee referee;



    public Long getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Long idPerson) {
        this.idPerson = idPerson;
    }

    public Person(Long id_person, String username, String password, String passwordConfirm) {
        this.idPerson = id_person;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public Person() {}

    public Long getId() {
        return idPerson;
    }

    public void setId(Long id) {
        this.idPerson = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String printYourRole() {
        Set<Role> yourRole = this.getRoles();
        String roles = "";
        for(Role r: yourRole) {
            roles = r.getName();
        }
        return roles;
    }

    public Long getIdCoach(){
        return coach.getIdCoach();
    }
}
