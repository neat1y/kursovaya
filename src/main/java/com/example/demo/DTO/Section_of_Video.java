package com.example.demo.DTO;

public class Section_of_Video {
    String section;
    Integer id;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Section_of_Video() {
    }

    public Section_of_Video(String section, Integer id) {
        this.section = section;
        this.id = id;
    }
}
