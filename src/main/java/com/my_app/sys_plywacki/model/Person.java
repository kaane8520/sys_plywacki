package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_person")
    private Long id_person;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;


    @Transient
    @Column(name = "passwordConfirm")
    private String passwordConfirm;

    @ManyToMany
    @Column(name = "roles")
    private Set<Role> roles;

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
