package com.example.demo.Entities;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer id;
    @Column(name="img")
    private String img;
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name ="block")
    private Boolean block;
    @Column(name="about")
    private String about;
    @Column(name = "gmail", unique = true, nullable = false)
    private String gmail;

    @Column(name = "person_role")
    private String person_role;


    @OneToMany(mappedBy = "person")
    private List<Person_Course> person_course;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public List<Person_Course> getPerson_course() {
        return person_course;
    }

    public void setPerson_course(List<Person_Course> person_course) {
        this.person_course = person_course;
    }

    public List<Person_Course> getCourseList() {
        return person_course;
    }

    public void setCourseList(List<Person_Course> courseList) {
        this.person_course = courseList;
    }
    
// Constructors, Getters, and Setters

    public Person() {
        // Default constructor

    }
    public Person(Person person){
        this.img=person.getImg();
        this.person_role=person.getPerson_role();
        this.gmail=person.getGmail();
        this.block=person.getBlock();
        this.name=person.getName();
        this.person_course=person.getPeron_course();
        this.password=person.getPassword();
        this.id=person.getPerson_id();
    }
    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public Person(String name, String password, String gmail, String personRole) {
        this.name = name;
        this.password = password;
        this.gmail = gmail;
        this.person_role = personRole;
    }

    // Getters and Setters

    public Integer getPersonId() {
        return id;
    }

    public void setPersonId(Integer personId) {
        this.id = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPersonRole() {
        return person_role;
    }

    public Integer getPerson_id() {
        return id;
    }

    public void setPerson_id(Integer person_id) {
        this.id = person_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPerson_role() {
        return person_role;
    }

    public void setPerson_role(String person_role) {
        this.person_role = person_role;
    }

    public List<Person_Course> getPeron_course() {
        return person_course;
    }

    public void setPeron_course(List<Person_Course> peron_course) {
        this.person_course = peron_course;
    }

    public void setPersonRole(String personRole) {
        this.person_role = personRole;
    }


}