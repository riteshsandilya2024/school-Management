package com.school.management.school_management_system.controller;


import com.school.management.school_management_system.entity.Teacher;
import com.school.management.school_management_system.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService service;

    @PostMapping
    public ResponseEntity<Teacher> create(@RequestBody Teacher t) {
        return ResponseEntity.ok(service.create(t));
    }

    @GetMapping
    @Cacheable(value = "teachers-all")
    public ResponseEntity<List<Teacher>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Cacheable(value = "teacher", key = "#id")
    public ResponseEntity<Teacher> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @CacheEvict(value = {"teachers-all","teacher"}, allEntries = true)
    public ResponseEntity<Teacher> update(@PathVariable Long id, @RequestBody Teacher t) {
        return ResponseEntity.ok(service.update(id, t));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = {"teachers-all","teacher"}, allEntries = true)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
