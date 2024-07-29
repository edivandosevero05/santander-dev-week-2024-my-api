package me.dio.controller.dto;

import me.dio.domain.model.Student;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public record StudentDto(
        Long id,
        String name,
        String date_of_birth,
        List<WorkshopDto> workshops,
        List<CourseDto> courses) {

    public StudentDto(Student model) {
        this(
                model.getId(),
                model.getName(),
                model.getDate_of_birth(),
                ofNullable(model.getWorkshops()).orElse(emptyList()).stream().map(WorkshopDto::new).collect(toList()),
                ofNullable(model.getCourses()).orElse(emptyList()).stream().map(CourseDto::new).collect(toList())
        );
    }

    public Student toModel() {
        Student model = new Student();
        model.setId(this.id);
        model.setName(this.name);
        model.setDate_of_birth(this.date_of_birth);
        model.setWorkshops(ofNullable(this.workshops).orElse(emptyList()).stream().map(WorkshopDto::toModel).collect(toList()));
        model.setCourses(ofNullable(this.courses).orElse(emptyList()).stream().map(CourseDto::toModel).collect(toList()));
        return model;
    }

}

