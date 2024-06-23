package com.example.demo.Repository;

import com.example.demo.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Person_Repository extends JpaRepository<Person,Integer> {

    Optional<Person> findByName(String Name);
    Optional<Person> findById(Integer Id);

}
