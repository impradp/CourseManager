package com.company.coursemanager.instructor.mapper;

import com.company.coursemanager.instructor.model.Instructor;
import com.company.coursemanager.instructor.model.InstructorDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InstructorMapper {
    /**
     * Maps the instructor to instructor dto.
     *
     * @param instructor The instructor entity.
     * @return Returns the instructor entity.
     */
    public static InstructorDTO toDTO(Instructor instructor) {
        InstructorDTO dto = new InstructorDTO();
        BeanUtils.copyProperties(instructor, dto, "password");
        return dto;
    }

    /**
     * Maps the list of instructors to instructor dto
     *
     * @param instructors The list of instructor entity
     * @return The list of instructor dto.
     */
    public static List<InstructorDTO> toDTO(List<Instructor> instructors) {
        return instructors.stream()
                .map(InstructorMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional instructor to optional instructor dto.
     *
     * @param instructor The instructor entity
     * @return The optional instructor dto.
     */
    public static Optional<InstructorDTO> toDTO(Optional<Instructor> instructor) {
        return instructor.map(InstructorMapper::toDTO);
    }

    /**
     * Maps the instructor dto  to the instructor entity.
     *
     * @param dto The instructor dto.
     * @return The instructor entity.
     */
    public static Instructor toEntity(InstructorDTO dto) {
        Instructor instructor = new Instructor();
        BeanUtils.copyProperties(dto, instructor);
        return instructor;
    }
}
