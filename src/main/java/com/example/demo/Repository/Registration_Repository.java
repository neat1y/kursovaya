package com.example.demo.Repository;

import com.example.demo.Entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Registration_Repository extends JpaRepository<Person,Integer> {

}
