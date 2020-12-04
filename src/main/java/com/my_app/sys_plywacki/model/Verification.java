package com.my_app.sys_plywacki.model;

import javax.persistence.*;
import com.my_app.sys_plywacki.service.*;
import com.my_app.sys_plywacki.repository.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Entity
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_verification;

    /*@ManyToOne
    Person person;*/

    @Column
    private Long idPerson;

    @Column(nullable = false)
    private String new_role;

    private String userName;

    private String oldRole;

    private Long idFile;

    private String fileName;

   /* @ManyToMany
    @Column(nullable = false)
    private Set<Role> newRoles;*/

    public Long getId_verification() {
        return id_verification;
    }

    public void setId_verification(Long id_verification) {
        this.id_verification = id_verification;
    }

    public Long getIdFile() {
        return idFile;
    }

    public void setIdFile(Long idFile) {
        this.idFile = idFile;
    }

   /* public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }*/
    public Long getIdPerson(){
        return this.idPerson;
    }
    public void setIdPerson(Long idPerson){
        this.idPerson = idPerson;
    }

    public String getOldRole(){
        return this.oldRole;
    }
    public void setOldRole(String oldRole){
        this.oldRole = oldRole;
    }

    public void setNew_role(String new_role){this.new_role = new_role;}

    public String getNew_role(){return this.new_role;}

    public void setUserName(String userName){this.userName = userName;}

    public String getUserName(){return this.userName;}

    public void setFileName(String fileName){this.fileName = fileName;}

    public String getFileName(){return this.fileName;}

    public String getYourRole(){
        PersonRepository personRepository = null;
        Optional<Person> p = personRepository.findById(this.getIdPerson());
        Set<Role> roles =  p.get().getRoles();
        String role = "";
        for (Role x : roles) {
            role = role+x.getName();
        }
        return role;
    }
    /*public void setNewRoles(Set<Role> roles) {

        this.newRoles = roles;
    }*/
    /*public Set<Role> getNewRoles() {
        return newRoles;
    }*/
}
