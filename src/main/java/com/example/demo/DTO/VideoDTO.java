package com.example.demo.DTO;

import com.example.demo.Entities.Course;

import javax.persistence.*;

public class VideoDTO {



    @Column(name = "name", length = 300)
    private String name;
    private Integer course_id ;
    private Integer video_id;

    public Integer getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Integer course_id) {
        this.course_id = course_id;
    }

    public Integer getVideo_id() {
        return video_id;
    }

    public void setVideo_id(Integer video_id) {
        this.video_id = video_id;
    }

    @Column(name = "section_of_course", length = 300)
    private String sectionOfCourse;

    // Constructors, Getters, and Setters

    public VideoDTO() {
        // Default constructor
    }

    public VideoDTO(String name, Integer course_id, Integer video_id, String sectionOfCourse) {
        this.name = name;
        this.course_id = course_id;
        this.video_id = video_id;
        this.sectionOfCourse = sectionOfCourse;
    }

    // Getters and Setters



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getSectionOfCourse() {
        return sectionOfCourse;
    }


    public void setSectionOfCourse(String sectionOfCourse) {
        this.sectionOfCourse = sectionOfCourse;
    }

}
