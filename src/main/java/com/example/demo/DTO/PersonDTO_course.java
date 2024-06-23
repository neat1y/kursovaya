package com.example.demo.DTO;

import com.example.demo.Entities.Person_Course;

import java.util.List;

public class PersonDTO_course {
    private String name;
    private String password;

    private String gmail;
    private List<String> role;
    private List<Integer> id;


    public List<String> getRole() {
        return role;
    }

    public void setRole(List<String> role) {
        this.role = role;
    }

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public PersonDTO_course(String name, String password, String gmail, List<String> role, List<Integer>  id) {
        this.name = name;
        this.password = password;
        this.gmail = gmail;
        this.role = role;
        this.id = id;
    }

}
