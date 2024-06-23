package com.example.demo.DTO;

import javax.validation.constraints.Email;

public class PersonDTO {

    private String name;
    private String password;

    private String email;


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

    public String getEmail() {
        return email;
    }

    public void setGmail(String gmail) {
        this.email = gmail;
    }

    public PersonDTO() {
    }
}
