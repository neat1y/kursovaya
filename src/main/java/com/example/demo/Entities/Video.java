package com.example.demo.Entities;


import javax.persistence.*;
@Entity
@Table(name="video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private int videoId;

    @Column(name = "name", length = 300)
    private String name;

    @Column(name = "reference", length = 1000)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;

    @Column(name = "section_of_course", length = 300)
    private String sectionOfCourse;

    // Constructors, Getters, and Setters

    public Video() {
        // Default constructor
    }

    public Video(String name, String reference, Course course, String sectionOfCourse) {
        this.name = name;
        this.reference = reference;
        this.course = course;
        this.sectionOfCourse = sectionOfCourse;
    }

    // Getters and Setters

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getSectionOfCourse() {
        return sectionOfCourse;
    }

    public void setSectionOfCourse(String sectionOfCourse) {
        this.sectionOfCourse = sectionOfCourse;
    }
}