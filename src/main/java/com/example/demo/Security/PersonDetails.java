package com.example.demo.Security;

import com.example.demo.DTO.PersonDTO_course;
import com.example.demo.Entities.Person;
import com.example.demo.Entities.Person_Course;
import com.example.demo.Service.Course_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PersonDetails implements UserDetails {

    private final PersonDTO_course person;


    public PersonDetails(PersonDTO_course person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = person.getRole(); // Получаем список ролей пользователя

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role)); // Преобразуем каждую строку в объект GrantedAuthority
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public PersonDTO_course getPerson() {
        return this.person;
    }
}
