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
    private Long id_person;
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

    @OneToMany
    private List<PlayerPersonConnection> players;

    public Long getId_person() {
        return id_person;
    }

    public void setId_person(Long id_person) {
        this.id_person = id_person;
    }

    public Person(Long id_person, String username, String password, String passwordConfirm) {
        this.id_person = id_person;
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }

    public Person() {}

    public Long getId() {
        return id_person;
    }

    public void setId(Long id) {
        this.id_person = id;
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


}
