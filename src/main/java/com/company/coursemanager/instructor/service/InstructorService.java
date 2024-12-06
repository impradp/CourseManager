package com.company.coursemanager.instructor.service;

import com.company.coursemanager.auth.helper.UserInfoDetails;
import com.company.coursemanager.instructor.mapper.InstructorMapper;
import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.model.InstructorDTO;
import com.company.coursemanager.instructor.repository.InstructorRepository;
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

import static com.company.coursemanager.utils.constants.InstructorConstants.*;

@Service
public class InstructorService implements IInstructorService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public List<InstructorDTO> findAll() {
        List<Instructor> instructor = this.instructorRepository.findAll();
        return InstructorMapper.toDTO(instructor);
    }

    @Override
    public InstructorDTO save(@NonNull Instructor instructor) {
        //Check if same user already exists during signup
        if (instructorRepository.existsByEmail(instructor.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        instructor.setPassword(encoder.encode(instructor.getPassword()));
        instructor.setRoles("INSTRUCTOR");
        Instructor savedInstructor = this.instructorRepository.save(instructor);

        return InstructorMapper.toDTO(savedInstructor);
    }

    @Override
    public InstructorDTO fetchById(long id) {
        Instructor instructor = findById(id);
        return InstructorMapper.toDTO(instructor);
    }

    private Instructor findById(long id) {
        return this.instructorRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        INSTRUCTOR.toLowerCase())));
    }

    @Override
    public Instructor fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        INSTRUCTOR.toLowerCase())));
    }

    public Optional<Instructor> findByEmail(@NonNull String emailId) {
        return this.instructorRepository.findByEmail(emailId);
    }

    @Override
    public String update(long id, @NonNull InstructorDTO instructorDTO) {
        Instructor authenticatedUser = fetchSelfInfo();

        //Allow update by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = findById(id);
        }

        if (StringUtils.isNotBlank(instructorDTO.getCountry())) {
            authenticatedUser.setCountry(instructorDTO.getCountry());
        }

        if (StringUtils.isNotBlank(instructorDTO.getName())) {
            authenticatedUser.setName(instructorDTO.getName());
        }

        if (StringUtils.isNotBlank(instructorDTO.getAddress())) {
            authenticatedUser.setAddress(instructorDTO.getAddress());
        }

        this.instructorRepository.save(authenticatedUser);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, INSTRUCTOR);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        Instructor authenticatedUser = fetchSelfInfo();

        //Allow to delete by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            authenticatedUser = findById(id);
        }

        this.instructorRepository.deleteById(authenticatedUser.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, INSTRUCTOR);
    }

}
