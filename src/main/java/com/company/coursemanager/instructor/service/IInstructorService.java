package com.company.coursemanager.instructor.service;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.model.InstructorDTO;
import com.company.coursemanager.utils.IGenericCrudService;

public interface IInstructorService extends IGenericCrudService<Instructor, InstructorDTO> {

    /**
     * Fetches the authenticated instructor info.
     *
     * @return The instructor dto
     */
    Instructor fetchSelfInfo();
}

