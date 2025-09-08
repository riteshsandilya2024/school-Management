package com.school.management.school_management_system.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}