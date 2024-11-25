package com.company.coursemanager.instructor.service;

import com.company.coursemanager.constants.InstructorConstants;
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
        return instructorRepository.findAll();
    }

    @Override
    public Instructor save(@NonNull Instructor instructor) {
        //TODO: Validate for save
        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor findById(long id) throws Exception {
        //TODO: Handle Not Found Exception with its own http codes.
        return instructorRepository.findById(id)
                .orElseThrow(()->new Exception(InstructorConstants.NOT_FOUND));
    }

    @Override
    public String update(@NonNull Instructor instructor) {
        //TODO: Validate for update
        instructorRepository.save(instructor);
        return InstructorConstants.UPDATE_SUCCESSFUL;
    }

    @Override
    public String deleteById(long id) {
        //TODO: Validate for deletion
        instructorRepository.deleteById(id);
        return InstructorConstants.DELETE_SUCCESSFUL;
    }

}
