package com.school.management.school_management_system.controller;


import com.school.management.school_management_system.security.JwtUtils;
import com.school.management.school_management_system.dto.RegisterRequest;
import com.school.management.school_management_system.entity.Student;
import com.school.management.school_management_system.entity.Teacher;
import com.school.management.school_management_system.entity.User;
import com.school.management.school_management_system.repository.RoleRepository;
import com.school.management.school_management_system.repository.UserRepository;
import com.school.management.school_management_system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("username exists");
        }

        Set<String> roleNames = req.getRoles();
        var roles = roleService.getRolesFromNames(roleNames);

        User u;
        if (roleNames != null && roleNames.contains("ROLE_STUDENT")) {
            Student s = new Student();
            s.setFirstName(req.getFirstName());
            s.setLastName(req.getLastName());
            s.setAge(req.getAge());
            s.setDepartment(req.getDepartment());
            u = s;
        } else if (roleNames != null && roleNames.contains("ROLE_TEACHER")) {
            Teacher t = new Teacher();
            t.setFirstName(req.getFirstName());
            t.setLastName(req.getLastName());
            t.setSubject(req.getSubject());
            u = t;
        } else {
            u = new User();
        }

        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRoles(roles);
        userRepository.save(u);
        return ResponseEntity.ok("registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody com.school.management.school_management_system.dto.AuthRequest req) {
        var userOpt = userRepository.findByUsername(req.getUsername());
        if (userOpt.isEmpty()) return ResponseEntity.status(401).body("invalid");
        var user = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) return ResponseEntity.status(401).body("invalid");
        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(new com.school.management.school_management_system.dto.AuthResponse(token));
    }
}
