package com.example.schoollistclient.models;

import java.io.Serializable;
import java.util.Date;

public class Teacher implements Serializable {
    private Integer id;
    private String first_name;
    private String surname;
    private String last_name;
    private String phone;
    private String email;
    private String password;
    public Teacher(Integer id, String first_name, String surname, String last_name, String phone, String email, String password, Date created_at, Date updated_at) {
        this.id = id;
        this.first_name = first_name;
        this.surname = surname;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        /*this.created_at = created_at;
        this.updated_at = updated_at;*/
    }

    public Teacher() {}

    public Teacher(Integer id, String first_name, String surname, String last_name, String phone, String email, String password/*, Date created_at*/) {
        this.id = id;
        this.first_name = first_name;
        this.surname = surname;
        this.last_name = last_name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        //this.created_at = created_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", surname='" + surname + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
               /* ", created_at=" + created_at +
                ", updated_at=" + updated_at +*/
                '}';
    }
}
