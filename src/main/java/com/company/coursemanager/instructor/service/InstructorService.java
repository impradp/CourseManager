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
    public InstructorDTO findById(long id) {
        Instructor instructor = this.instructorRepository.findById(id).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        INSTRUCTOR.toLowerCase())));
        return InstructorMapper.toDTO(instructor);
    }

    public InstructorDTO fetchSelfInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = ((UserInfoDetails) authentication.getPrincipal()).getUsername();
        return findByEmail(email).orElseThrow(
                () -> new GlobalExceptionWrapper.NotFoundException(String.format(NOT_FOUND_MESSAGE,
                        INSTRUCTOR.toLowerCase())));
    }

    public Optional<InstructorDTO> findByEmail(@NonNull String emailId) {
        Optional<Instructor> instructor = this.instructorRepository.findByEmail(emailId);
        return InstructorMapper.toDTO(instructor);
    }

    @Override
    public String update(long id, @NonNull InstructorDTO instructorDTO) {
        InstructorDTO authenticatedUser = fetchSelfInfo();
        Instructor instructorEntity = InstructorMapper.toEntity(authenticatedUser);

        //Allow update by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            instructorEntity = InstructorMapper.toEntity(findById(id));
        }

        if (StringUtils.isNotBlank(instructorDTO.getCountry())) {
            instructorEntity.setCountry(instructorDTO.getCountry());
        }

        if (StringUtils.isNotBlank(instructorDTO.getName())) {
            instructorEntity.setName(instructorDTO.getName());
        }

        if (StringUtils.isNotBlank(instructorDTO.getAddress())) {
            instructorEntity.setAddress(instructorDTO.getAddress());
        }
        
        this.instructorRepository.save(instructorEntity);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, INSTRUCTOR);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        InstructorDTO authenticatedUser = fetchSelfInfo();
        Instructor instructorEntity = InstructorMapper.toEntity(authenticatedUser);

        //Allow to delete by admin to the instructor info.
        if (Arrays.stream(authenticatedUser.getRoles().split(",")).anyMatch(role -> role.trim().equalsIgnoreCase(
                "ADMIN"))) {
            instructorEntity = InstructorMapper.toEntity(findById(id));
        }

        this.instructorRepository.deleteById(instructorEntity.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, INSTRUCTOR);
    }

}
