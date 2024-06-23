package com.example.demo.DTO;

public class PersonLoginDTO {

    private String Username;
    private String password;

    public PersonLoginDTO(String Username, String password) {
        this.Username = Username;
        this.password = password;
    }

    public String getName() {
        return Username;
    }

    public void setName(String name) {
        this.Username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
