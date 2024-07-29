package me.dio.service.impl;

import me.dio.domain.model.Student;
import me.dio.domain.repository.StudentRepository;
import me.dio.service.StudentService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Long UNCHANGEABLE_USER_ID = 1L;

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return this.studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return this.studentRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Student create(Student studentToCreate) {
        ofNullable(studentToCreate).orElseThrow(() -> new BusinessException("Student to create must not be null."));
        ofNullable(studentToCreate.getCourses()).orElseThrow(() -> new BusinessException("Student workshop must not be null."));
        ofNullable(studentToCreate.getCourses()).orElseThrow(() -> new BusinessException("Student course must not be null."));

        this.validateChangeableId(studentToCreate.getId(), "created");
        return this.studentRepository.save(studentToCreate);
    }

    @Transactional
    public Student update(Long id, Student studentToUpdate) {
        this.validateChangeableId(id, "updated");
        Student dbStudent = this.findById(id);
        if (!dbStudent.getId().equals(studentToUpdate.getId())) {
            throw new BusinessException("Update IDs must be the same.");
        }

        dbStudent.setName(studentToUpdate.getName());
        dbStudent.setDate_of_birth(studentToUpdate.getDate_of_birth());
        dbStudent.setWorkshops(studentToUpdate.getWorkshops());
        dbStudent.setCourses(studentToUpdate.getCourses());

        return this.studentRepository.save(dbStudent);
    }

    @Transactional
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        Student dbStudent = this.findById(id);
        this.studentRepository.delete(dbStudent);
    }

    private void validateChangeableId(Long id, String operation) {
        if (UNCHANGEABLE_USER_ID.equals(id)) {
            throw new BusinessException("Student with ID %d can not be %s.".formatted(UNCHANGEABLE_USER_ID, operation));
        }
    }
}

