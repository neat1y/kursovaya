package com.example.demo.DTO;

public class AuthDTO {
    private String name;
    private String password;

    public AuthDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public AuthDTO(){

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
}
