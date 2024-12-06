package com.company.coursemanager.instructor.controller;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.model.InstructorDTO;
import com.company.coursemanager.instructor.service.InstructorService;
import com.company.coursemanager.utils.RestHelper;
import com.company.coursemanager.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    /**
     * Fetch self info of the instructor
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", instructorService.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the instructor by identifier.
     *
     * @param id The unique identifier of the instructor.
     * @return The instructor entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", instructorService.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the instructor entities in the system.
     *
     * @return The list of instructor entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructors", instructorService.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Signing up the new instructor.
     *
     * @param instructor The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody Instructor instructor) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("instructor", instructorService.save(instructor));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing instructor entity.
     *
     * @param instructorDTO The updated instructor dto.
     * @return The message indicating the confirmation on updated instructor entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('INSTRUCTOR','ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id,
                                               @Validated @RequestBody InstructorDTO instructorDTO) {
        String message = instructorService.update(id, instructorDTO);
        return RestHelper.responseMessage(message);
    }

    /**
     * Deletes the instructor by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted instructor entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = instructorService.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
