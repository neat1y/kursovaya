package com.example.demo.Repository;

import com.example.demo.Entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Course_Repository extends JpaRepository<Course,Integer> {
        List<Course> findAllBy();

        List<Course> findByNameContainingIgnoreCase(String name);
        Optional<Course> findByCourseId(int id);

        Optional<Course> findByName(String name);

}
