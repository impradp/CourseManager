package com.company.coursemanager.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String address;
    private String country;
    private boolean isFullTimer;
    private Long courseId;
    private String email;
    private String roles;
}
