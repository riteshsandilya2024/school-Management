package com.school.management.school_management_system.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Teacher extends User {
    private String firstName;
    private String lastName;
    private String subject;
}
