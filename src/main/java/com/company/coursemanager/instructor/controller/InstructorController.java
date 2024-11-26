package com.company.coursemanager.instructor.controller;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.service.InstructorService;
import com.company.coursemanager.utils.RestHelper;
import com.company.coursemanager.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    /**
     * Fetches the instructor by identifier.
     *
     * @param id The unique identifier of the instructor.
     * @return The instructor entity.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", instructorService.findById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the instructor entities in the system.
     *
     * @return The list of instructor entities.
     */
    @GetMapping
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructors", instructorService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Saves the new instructor entity.
     *
     * @param instructor The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated Instructor instructor) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", instructorService.save(instructor));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing instructor entity.
     *
     * @param instructor The updated instructor entity.
     * @return The message indicating the confirmation on updated instructor entity.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @Validated Instructor instructor) {
        String message = instructorService.update(id, instructor);
        return RestHelper.responseMessage(message);
    }

    /**
     * Deletes the instructor entity by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted instructor entity.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> update(@PathVariable long id) {
        String message = instructorService.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
