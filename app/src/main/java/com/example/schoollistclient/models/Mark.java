package com.example.schoollistclient.models;

import java.io.Serializable;

public class Mark implements Serializable {
    private Integer id;
    private Integer id_student;
    private Integer subject_id;
    private Integer workload_id;
    private String date;
    private Integer value;
    public Mark() {}
    public Mark(Integer id, Integer id_student, Integer subject_id, Integer workload_id, Integer value) {
        this.id = id;
        this.id_student = id_student;
        this.subject_id = subject_id;
        this.workload_id = workload_id;
        this.value = value;
    }

    public Mark(Integer id, Integer id_student, Integer subject_id, Integer workload_id, String date, Integer value) {
        this.id = id;
        this.id_student = id_student;
        this.subject_id = subject_id;
        this.workload_id = workload_id;
        this.date = date;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_student() {
        return id_student;
    }

    public void setId_student(Integer id_student) {
        this.id_student = id_student;
    }

    public Integer getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(Integer subject_id) {
        this.subject_id = subject_id;
    }

    public Integer getWorkload_id() {
        return workload_id;
    }

    public void setWorkload_id(Integer workload_id) {
        this.workload_id = workload_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
