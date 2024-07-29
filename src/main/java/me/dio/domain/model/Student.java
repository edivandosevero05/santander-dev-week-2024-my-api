package me.dio.domain.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.*;

@Entity(name = "tb_student")
public class Student  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String date_of_birth;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Workshop> workshops;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public List<Workshop> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<Workshop> workshops) {
        this.workshops = workshops;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
