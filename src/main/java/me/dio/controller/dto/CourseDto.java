package me.dio.controller.dto;

import me.dio.domain.model.Course;

public record CourseDto(Long id, String name, String instructor, String date ) {

    public CourseDto(Course model) {
        this(model.getId(), model.getName(), model.getInstructor(), model.getDate());
    }

    public Course toModel() {
        Course model = new Course();
        model.setId(this.id);
        model.setName(this.name);
        model.setInstructor(this.instructor);
        model.setDate(this.date);
        return model;
    }
}

