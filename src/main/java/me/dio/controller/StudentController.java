package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.dio.controller.dto.StudentDto;
import me.dio.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/students")
@Tag(name = "student Controller", description = "RESTful API for managing students.")
public record StudentController(StudentService studentService) {

    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve a list of all registered students")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operation successful")
    })
    public ResponseEntity<List<StudentDto>> findAll() {
        var student = studentService.findAll();
        var studentDto = student.stream().map(StudentDto::new).collect(Collectors.toList());
        return ResponseEntity.ok(studentDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a student by ID", description = "Retrieve a specific user based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<StudentDto> findById(@PathVariable Long id) {
        var student = studentService.findById(id);
        return ResponseEntity.ok(new StudentDto(student));
    }

    @PostMapping
    @Operation(summary = "Create a new student", description = "Create a new student and return the created user's data")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "201", description = "Student created successfully"),
            @ApiResponse(responseCode = "422", description = "Invalid student data provided")
    })
    public ResponseEntity<StudentDto> create(@RequestBody StudentDto userDto) {
        var student = studentService.create(userDto.toModel());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();
        return ResponseEntity.created(location).body(new StudentDto(student));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a student", description = "Update the data of an existing user based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Student updated successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found"),
            @ApiResponse(responseCode = "422", description = "Invalid student data provided")
    })
    public ResponseEntity<StudentDto> update(@PathVariable Long id, @RequestBody StudentDto userDto) {
        var student = studentService.update(id, userDto.toModel());
        return ResponseEntity.ok(new StudentDto(student));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a student", description = "Delete an existing student based on its ID")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
