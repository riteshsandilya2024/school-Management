package com.school.management.school_management_system.service;


import com.school.management.school_management_system.entity.Student;
import com.school.management.school_management_system.KafkaProducerConsumer.ProducerService;
import com.school.management.school_management_system.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    private final ProducerService producer;

    public StudentService(StudentRepository repo, ProducerService producer) { this.repo = repo; this.producer = producer; }

    public Student create(Student s) {
        Student saved = repo.save(s);
        producer.sendEvent("school.events", "student.created:" + saved.getId());
        return saved;
    }
    public List<Student> findAll() { return repo.findAll(); }
    public Student findById(Long id) { return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found")); }
    public Student update(Long id, Student s) {
        Student existing = findById(id);
        existing.setFirstName(s.getFirstName());
        existing.setLastName(s.getLastName());
        existing.setAge(s.getAge());
        existing.setDepartment(s.getDepartment());
        Student updated = repo.save(existing);
        producer.sendEvent("school.events", "student.updated:" + updated.getId());
        return updated;
    }
    public void delete(Long id) { repo.deleteById(id); producer.sendEvent("school.events", "student.deleted:" + id); }
}
