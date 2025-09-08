package com.school.management.school_management_system.controller;


import com.school.management.school_management_system.security.JwtUtils;
import com.school.management.school_management_system.entity.User;
import com.school.management.school_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/student")
public class StudentAuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> studentLogin(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isStudent = user.getRoles().stream().anyMatch(r -> r.getName().equals("ROLE_STUDENT"));
        if (!isStudent) {
            return ResponseEntity.badRequest().body(Map.of("error", "Only STUDENTS can login here"));
        }

        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(Map.of("token", token, "role", "STUDENT"));
    }
}
