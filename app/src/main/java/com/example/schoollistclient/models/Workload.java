package com.example.schoollistclient.models;
public class Workload {
    private Integer id;
    private String name_workload;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName_workload() {
        return name_workload;
    }

    public void setName_workload(String name_workload) {
        this.name_workload = name_workload;
    }

    public Workload(Integer id, String name_workload) {
        this.id = id;
        this.name_workload = name_workload;
    }

    public Workload(String name_workload) {
        this.name_workload = name_workload;
    }

    public Workload() {
    }
}
