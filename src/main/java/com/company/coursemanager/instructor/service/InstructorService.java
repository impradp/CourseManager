package com.company.coursemanager.instructor.service;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.repository.InstructorRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService implements IInstructorService{
    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public List<Instructor> findAll() {
        return List.of();
    }

    @Override
    public Instructor save(@NonNull Instructor template) {
        return instructorRepository.save(template);
    }

    @Override
    public Instructor findById(long id) {
        return null;
    }

    @Override
    public String update(Instructor entity) {
        return "";
    }

    @Override
    public String deleteById(long id) {
        return "";
    }
}
