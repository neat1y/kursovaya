package com.example.demo.Repository;

import com.example.demo.Entities.Course;
import com.example.demo.Entities.Person;
import com.example.demo.Entities.PersonCourseId;
import com.example.demo.Entities.Person_Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Person_Course_Repository extends JpaRepository<Person_Course, PersonCourseId> {
    List<Person_Course> findById_PersonId(Integer person_id);
    Optional<Person_Course> findById_PersonIdAndId_CourseId(Integer person_id,Integer course_id);
    List<Person_Course> findById_PersonIdAndRole(Integer person_id,String role);
    List<Person_Course> findById_CourseIdAndRole(Integer course_id ,String qwe);
    Optional<Person_Course> findById_CourseIdAndId_PersonId(Integer course,Integer person);
}
