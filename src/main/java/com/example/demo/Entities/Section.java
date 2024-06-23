package com.example.demo.Entities;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "section")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private int sectionId;

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "section")
    private List<Course> courseList;

    // Constructors, Getters, and Setters

    public Section() {
        // Default constructor
    }

    public Section(String name) {
        this.name = name;
    }

    // Getters and Setters

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
