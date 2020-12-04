package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFile;

    private Long idPerson;

    private String name;

    @Lob
    @Column(name="content")
    private byte[] content;

    public void setIdFile(Long idFile){
        this.idFile = idFile;
    }
    public Long getIdFile(){
        return this.idFile;
    }

    public void setIdPerson(Long idPerson){
        this.idPerson = idPerson;
    }
    public Long getIdPerson(){
        return this.idPerson;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public byte[] getContent(){
        return this.content;
    }

    public void setContent(byte[] pic){
        this.content = pic;
    }
}
