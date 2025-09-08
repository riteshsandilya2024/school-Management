package com.school.management.school_management_system.service;


import com.school.management.school_management_system.entity.Role;
import com.school.management.school_management_system.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRolesFromNames(Set<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        roleNames.forEach(name -> roleRepository.findByName(name).ifPresent(roles::add));
        return roles;
    }
}
