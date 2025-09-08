package com.school.management.school_management_system.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles; // e.g. ["ROLE_STUDENT"]

    // optional student/teacher fields
    private String firstName;
    private String lastName;
    private Integer age;
    private String department;
    private String subject;
}
