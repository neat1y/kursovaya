package com.example.demo.DTO;

import com.example.demo.Entities.Section;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class CourseDTO {
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "section_id", referencedColumnName = "section_id")
    private Section section;

    @Column(name = "description", length = 10000)
    private String description;


    @Column(name="img")
    private String img;
    @Column(name="rate")
    private Integer rate ;


    public CourseDTO(Integer id, String name, Section section, String description, String img,Integer rate) {
        this.id = id;
        this.name = name;
        this.section = section;
        this.description = description;
        this.img = img;
        this.rate=rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CourseDTO() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
