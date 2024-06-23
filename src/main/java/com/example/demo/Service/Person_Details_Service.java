package com.example.demo.Service;

import com.example.demo.DTO.PersonDTO_course;
import com.example.demo.Entities.Person;
import com.example.demo.Repository.Person_Repository;
import com.example.demo.Security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Person_Details_Service implements UserDetailsService {
    private final Person_Repository personRepository;
    @Autowired
    public Person_Details_Service(Person_Repository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByName(username);
        if (person.isEmpty() ) {
            throw new UsernameNotFoundException("User not found");
        }
        // Здесь вы можете добавить дополнительную логику проверки пароля
        PersonDTO_course personDTOCourse=new PersonDTO_course(person.get().getName(),person.get().getPassword(),person.get().getGmail()
        ,List.of( person.get().getPersonRole()),List.of(1));
        return new PersonDetails(personDTOCourse);
    }

}
