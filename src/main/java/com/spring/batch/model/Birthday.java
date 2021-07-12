package com.spring.batch.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "birthdays")
public class Birthday {

    @Id
    @Column(name = "email_id")
    String emailId;
    @Column(name = "name")
    String name;
    @Column(name = "birthday")
    Timestamp birthday;
    @Column(name = "wishes_status")
    String wishesStatus;


    public Birthday(String emailId, String name, Timestamp birthday, String wishesStatus) {
        this.emailId = emailId;
        this.name = name;
        this.birthday = birthday;
        this.wishesStatus = wishesStatus;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getWishesStatus() {
        return wishesStatus;
    }

    public void setWishesStatus(String wishesStatus) {
        this.wishesStatus = wishesStatus;
    }

    public Birthday() {
    }
}
