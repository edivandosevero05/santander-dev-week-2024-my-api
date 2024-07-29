package me.dio.controller.dto;

import me.dio.domain.model.Workshop;

public record WorkshopDto(Long id, String name, String instructor, String date ) {

    public WorkshopDto(Workshop model) {
        this(model.getId(), model.getName(), model.getInstructor(), model.getDate());
    }

    public Workshop toModel() {
        Workshop model = new Workshop();
        model.setId(this.id);
        model.setName(this.name);
        model.setInstructor(this.instructor);
        model.setDate(this.date);
        return model;
    }
}

