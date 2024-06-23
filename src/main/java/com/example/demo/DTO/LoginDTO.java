package com.example.demo.DTO;


import javax.validation.constraints.Email;

public class LoginDTO {
    private Integer id;
    private String Username;


    private String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return Username;
    }

    public void setName(String name) {
        this.Username = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginDTO() {
    }

    public LoginDTO(Integer id, String username, String email) {
        this.id = id;
        Username = username;
        this.email = email;
    }
}
