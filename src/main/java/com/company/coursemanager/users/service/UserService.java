package com.company.coursemanager.users.service;

import com.company.coursemanager.users.model.User;
import com.company.coursemanager.users.model.UserDTO;
import com.company.coursemanager.utils.IGenericCrudService;

public interface UserService extends IGenericCrudService<User, UserDTO> {

    /**
     * Fetches the authenticated user info.
     *
     * @return The user dto
     */
    UserDTO fetchSelfInfo();

    /**
     * Updates the user entity.
     *
     * @param user The user entity to be updated.
     * @return The confirmation message on whether the user is updated or not.
     */
    String updateEntity(User user);
}

