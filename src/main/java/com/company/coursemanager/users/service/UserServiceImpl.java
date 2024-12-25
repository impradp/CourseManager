package com.company.coursemanager.users.service;

import com.company.coursemanager.auth.helper.UserInfoDetails;
import com.company.coursemanager.users.mapper.UserMapper;
import com.company.coursemanager.users.model.User;
import com.company.coursemanager.users.model.UserDTO;
import com.company.coursemanager.users.repository.UserRepository;
import com.company.coursemanager.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.company.coursemanager.utils.constants.UserConstants.DELETED_SUCCESSFULLY_MESSAGE;
import static com.company.coursemanager.utils.constants.UserConstants.DUPLICATE_EMAIL_MESSAGE;
import static com.company.coursemanager.utils.constants.UserConstants.NOT_FOUND_MESSAGE;
import static com.company.coursemanager.utils.constants.UserConstants.UPDATED_SUCCESSFULLY_MESSAGE;
import static com.company.coursemanager.utils.constants.UserConstants.USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDTO> findAll() {
        List<User> user = this.userRepository.findAll();
        return UserMapper.toDTO(user);
    }

    @Override
    public UserDTO save(@NonNull User user) {
        //Check if same user already exists during signup
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("USER");
        User savedUser = this.userRepository.save(user);

        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO fetchById(long id) {
        User user = findById(id);
        return UserMapper.toDTO(user);
    }

    private User findById(long id) {
        return this.userRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
    }

    @Override
    public UserDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        User selectedUser = findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        USER.toLowerCase())));
        return UserMapper.toDTO(selectedUser);
    }

    public Optional<User> findByEmail(@NonNull String emailId) {
        return this.userRepository.findByEmail(emailId);
    }

    @Override
    public String update(long id, @NonNull UserDTO userDTO) {
        UserDTO authenticatedUser = fetchSelfInfo();

        //Allow update by admin to the user info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = UserMapper.toDTO(findById(id));
        }

        if (StringUtils.isNotBlank(userDTO.getCountry())) {
            authenticatedUser.setCountry(userDTO.getCountry());
        }

        if (StringUtils.isNotBlank(userDTO.getName())) {
            authenticatedUser.setName(userDTO.getName());
        }

        if (StringUtils.isNotBlank(userDTO.getAddress())) {
            authenticatedUser.setAddress(userDTO.getAddress());
        }

        return updateEntity(UserMapper.toEntity(authenticatedUser));
    }

    @Override
    public String updateEntity(User user) {
        this.userRepository.save(user);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        UserDTO authenticatedUser = fetchSelfInfo();

        //Allow to delete by admin to the user info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = UserMapper.toDTO(findById(id));
        }

        this.userRepository.deleteById(authenticatedUser.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }

}
