package com.example.demo.Service;

import com.example.demo.Entities.Person;
import com.example.demo.Repository.Person_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Person_Service {
    private final Person_Repository personRepository;
    @Autowired
    public Person_Service(Person_Repository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> all(){
        return personRepository.findAll();
    }
    public Optional<Person> findbyId(Integer id){
        return personRepository.findById(id);
    }
    public void save(Person person){
        personRepository.save(person);
    }
    public Optional<Person> findbyName(String name) {
        return personRepository.findByName(name);
    }
}
