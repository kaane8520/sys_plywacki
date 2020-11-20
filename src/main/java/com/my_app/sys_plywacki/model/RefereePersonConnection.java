package com.my_app.sys_plywacki.model;



import javax.persistence.*;

@Entity
public class RefereePersonConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Referee referee;

    @ManyToOne
    Person person;

    public RefereePersonConnection(Referee referee, Person person) {
        this.referee = referee;
        this.person = person;
    }

    public RefereePersonConnection() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getIdReferee(){
        return referee.getIdReferee();
    }
    public Long getIdPerson(){
        return person.getId_person();
    }
}
