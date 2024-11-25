package com.company.coursemanager.instructor.controller;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @PostMapping
    public Instructor saveInstructor(@Validated @RequestBody Instructor instructor)
    {
        return instructorService.save(instructor);
    }
}
