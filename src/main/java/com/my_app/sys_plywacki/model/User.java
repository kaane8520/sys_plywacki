package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User extends Person{

    @ManyToMany
    private Set<Role> roles;
}