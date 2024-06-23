package com.example.demo.Repository;

import com.example.demo.Entities.Person;
import com.example.demo.Entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Section_Repository  extends JpaRepository<Section,Integer> {
    public Optional<Section> findByName(String name);
}
