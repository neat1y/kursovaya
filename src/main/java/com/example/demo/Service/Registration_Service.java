package com.example.demo.Service;

import com.example.demo.Entities.Person;
import com.example.demo.Repository.Registration_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Registration_Service {
    private final Registration_Repository registrationRepository;
    @Autowired
    public Registration_Service(Registration_Repository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }
    public void reg(Person person){
        person.setImg("D:\\curs\\2.jpg\\");
        registrationRepository.save(person);
    }
}
