package com.company.coursemanager.users.controller;

import com.company.coursemanager.users.model.User;
import com.company.coursemanager.users.model.UserDTO;
import com.company.coursemanager.users.service.UserServiceImpl;
import com.company.coursemanager.utils.RestHelper;
import com.company.coursemanager.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Fetch self info of the user
     *
     * @return The details of the authenticated user.
     */
    @GetMapping("/self")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> fetchSelf() {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the user by identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the user entities in the system.
     *
     * @return The list of user entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("users", userServiceImpl.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        Map<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Updates the existing user entity.
     *
     * @param userDTO The updated user dto.
     * @return The message indicating the confirmation on updated user entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public ResponseEntity<RestResponse> update(@PathVariable long id,
                                               @Validated @RequestBody UserDTO userDTO) {
        String message = userServiceImpl.update(id, userDTO);
        return RestHelper.responseMessage(message);
    }

    /**
     * Deletes the user by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted user entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userServiceImpl.deleteById(id);
        return RestHelper.responseMessage(message);
    }
}
