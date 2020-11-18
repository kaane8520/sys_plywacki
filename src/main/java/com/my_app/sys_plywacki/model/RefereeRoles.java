package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class RefereeRoles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idrefereerole;
    private String refereerolename;


    public RefereeRoles() {
    }



    public Long getIdrefereerole() {
        return idrefereerole;
    }

    public void setIdrefereerole(Long id) {
        this.idrefereerole = id;
    }

    public String getRefereerolename() {
        return refereerolename;
    }

    public void setRefereerolename(String refereerolename) {
        this.refereerolename = refereerolename;
    }

}
