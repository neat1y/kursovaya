package com.example.demo.Service;

import com.example.demo.DTO.CourseDTO;
import com.example.demo.Entities.Course;
import com.example.demo.Entities.Person;
import com.example.demo.Entities.PersonCourseId;
import com.example.demo.Entities.Person_Course;
import com.example.demo.Repository.Person_Course_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Person_Course_Service {
    private final Person_Course_Repository personCourseRepository;
    @Autowired
    public Person_Course_Service(Person_Course_Repository personCourseRepository) {
        this.personCourseRepository = personCourseRepository;
    }
    public List<CourseDTO> your_course(Integer id){
       return   personCourseRepository.findById_PersonId(id).stream().map(Course->new CourseDTO(Course.getCourse().getCourseId(),
               Course.getCourse().getName(),Course.getCourse().getSection(),Course.getCourse().getDescription(),Course.getCourse().getImg(),Course.getCourse().getRate())).toList();
    }

    public Boolean checkList(Integer course,Integer person){
      Optional<Person_Course> personCourse=personCourseRepository.findById_CourseIdAndId_PersonId(course,person);
      if(personCourse.isPresent() && !personCourse.get().getRole().equals("ROLE_WISH")){
          return Boolean.TRUE;
      }
            return Boolean.FALSE;
    }
    public Optional<Person_Course> person_course(Integer person_id, Integer course_id){
        return personCourseRepository.findById_PersonIdAndId_CourseId(person_id,course_id);
    }
    public List<CourseDTO> your_teacher_course(Integer id){
        return personCourseRepository.findById_PersonIdAndRole(id,"ROLE_TEACHER").stream().map(Course->new CourseDTO(Course.getCourse().getCourseId(),
                Course.getCourse().getName(),Course.getCourse().getSection(),Course.getCourse().getDescription(),Course.getCourse().getImg(),Course.getCourse().getRate())).toList();
    }
    public List<Person>  teacher_id(Integer id)
    {
        return  personCourseRepository.findById_CourseIdAndRole(id,"ROLE_TEACHER").stream().map(personCourse -> new Person(personCourse.getPerson())).toList();
    }

    public List<CourseDTO> find_wish(Integer id) {
        return personCourseRepository.findById_PersonIdAndRole(id,"ROLE_WISH").stream().map(Course->new CourseDTO(Course.getCourse().getCourseId(),
                Course.getCourse().getName(),Course.getCourse().getSection(),Course.getCourse().getDescription(),Course.getCourse().getImg(),Course.getCourse().getRate())).toList();
    }
    public void save_student(Course course, Person person) {
        PersonCourseId personCourseId=new PersonCourseId();
        personCourseId.setCourseId(course.getCourseId());
        personCourseId.setPersonId(person.getPersonId());
        Person_Course personCourse=new Person_Course();
        personCourse.setCourse(course);
        personCourse.setPerson(person);
        personCourse.setId(personCourseId);
        personCourse.setRole("ROLE_STUDENT");
        personCourseRepository.save(personCourse);
    }

    public void save_wish(Course course, Person person) {
        PersonCourseId personCourseId=new PersonCourseId();
        personCourseId.setCourseId(course.getCourseId());
        personCourseId.setPersonId(person.getPersonId());
        Person_Course personCourse=new Person_Course();
        personCourse.setCourse(course);
        personCourse.setPerson(person);
        personCourse.setId(personCourseId);
        personCourse.setRole("ROLE_WISH");
        personCourseRepository.save(personCourse);
    }

    public void save_teacher(Course course, Person person) {
        PersonCourseId personCourseId=new PersonCourseId();
        personCourseId.setCourseId(course.getCourseId());
        personCourseId.setPersonId(person.getPersonId());
        Person_Course personCourse=new Person_Course();
        personCourse.setCourse(course);
        personCourse.setPerson(person);
        personCourse.setId(personCourseId);
        personCourse.setRole("ROLE_TEACHER");
        personCourseRepository.save(personCourse);
    }
}
