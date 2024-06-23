package com.example.demo.Service;

import com.example.demo.Entities.Course;
import com.example.demo.Entities.Person;
import com.example.demo.Entities.Person_Course;
import com.example.demo.Repository.Course_Repository;
import com.example.demo.Repository.Person_Course_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Course_Service {
    private final Course_Repository courseRepository;
    private final Person_Course_Repository personCourseRepository;

    @Autowired
    public Course_Service(Course_Repository courseRepository, Person_Course_Repository personCourseRepository) {
        this.courseRepository = courseRepository;
        this.personCourseRepository = personCourseRepository;
    }

    public List<Course>  all(){
        return courseRepository.findAll();
    }

    public List<Course>  findNameAll(String name){return courseRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Course>  teacher_course(){
        return courseRepository.findAll();
    }
    public Course  find_by_name(String name){
        return courseRepository.findByName(name).get();
    }

    public List<Course>  teacher_course(Integer id){
        return  personCourseRepository.findById_PersonId(id).stream().map(Person_Course::getCourse).toList();
    }
    public void save(Course course){
        courseRepository.save(course);
    }
    public Optional<Course> find_id(int id) {
        return courseRepository.findByCourseId(id);
    }


}
