package com.my_app.sys_plywacki.model;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMessage;

    @Column(name="id_person")
    private Long idPerson;

    private String content;

    public Message(){}
    public Message(String content) {
        this.content = content;
    }

    public void setIdMessage(Long idMessage){
        this.idMessage = idMessage;
    }
    public Long getIdMessage(){
        return this.idMessage;
    }

    public void setIdPerson(Long idPerson){
        this.idPerson = idPerson;
    }
    public Long getIdPerson(){
        return this.idPerson;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
}
