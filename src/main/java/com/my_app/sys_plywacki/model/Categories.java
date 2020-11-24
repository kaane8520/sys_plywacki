package com.my_app.sys_plywacki.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcategory;
    private String namecategory;

    public Categories() {
    }

    public Long getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(Long idcategory) {
        this.idcategory = idcategory;
    }

    public String getNamecategory() {
        return namecategory;
    }

    public void setNamecategory(String namecategory) {
        this.namecategory = namecategory;
    }
}
