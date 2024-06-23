package com.example.demo.Entities;

import javax.persistence.*;
@Entity
@Table(name = "person_course")
public class Person_Course {

    @EmbeddedId
    private PersonCourseId id;
    @Column(name="rate_course")
    private Integer rate_course;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("personId")
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person person;

    @Column(name = "description")
    private String description;

    @Column(name = "role")
    private String role;

    public PersonCourseId getId() {
        return id;
    }

    public void setId(PersonCourseId id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public Integer getRate_course() {
        return rate_course;
    }

    public void setRate_course(Integer rate_course) {
        this.rate_course = rate_course;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Person_Course() {
    }
}
