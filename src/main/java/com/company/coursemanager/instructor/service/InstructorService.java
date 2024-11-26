package com.company.coursemanager.instructor.service;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.repository.InstructorRepository;
import com.company.coursemanager.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.company.coursemanager.utils.constants.InstructorConstants.*;

@Service
public class InstructorService implements IInstructorService{
    @Autowired
    private InstructorRepository instructorRepository;


    @Override
    public List<Instructor> findAll() {
        return this.instructorRepository.findAll();
    }

    @Override
    public Instructor save(@NonNull Instructor entity) {
        return this.instructorRepository.save(entity);
    }

    @Override
    public Instructor findById(long id) {
        return this.instructorRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE, INSTRUCTOR.toLowerCase())));
    }

    @Override
    public String update(long id, @NonNull Instructor entity) {
        Instructor selectedInstructor = findById(id);
        selectedInstructor.setCountry(entity.getCountry());
        selectedInstructor.setName(entity.getName());
        selectedInstructor.setAddress(entity.getAddress());

        this.instructorRepository.save(selectedInstructor);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, INSTRUCTOR);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        findById(id);
        this.instructorRepository.deleteById(id);
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, INSTRUCTOR);
    }

}
