package com.example.demo.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name ="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name ="rate")
    private Integer rate;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name="img")
    private String img;
    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section;
    @OneToMany(mappedBy = "course")
    private List<Video> videoList;

    @Column(name = "description", length = 10000)
    private String description;

    // Constructors, Getters, and Setters
    @OneToMany(mappedBy = "course")
    private List<Person_Course> personList;


    public Course() {
        // Default constructor
    }



    public List<Person_Course> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person_Course> personList) {
        this.personList = personList;
    }

    public Course(String name, Section section, String description) {
        this.name = name;
        this.section = section;
        this.description = description;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
    // Getters and Setters

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", name='" + name + '\'' +
                ", section=" + section +
                ", videoList=" + videoList +
                ", description='" + description + '\'' +
                ", personList=" + personList +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setImg(String img) {
        this.img = img;
    }
}