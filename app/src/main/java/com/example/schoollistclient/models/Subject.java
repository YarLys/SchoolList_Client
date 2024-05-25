package com.example.schoollistclient.models;
public class Subject {
    private Integer id;
    private String name_subject;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name_subject;
    }

    public void setName(String name) {
        this.name_subject = name;
    }

    public Subject() {}

    public Subject(Integer id, String name_subject) {
        this.id = id;
        this.name_subject = name_subject;
    }

    public Subject(String name_subject) {
        this.name_subject = name_subject;
    }
}
