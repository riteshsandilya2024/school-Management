package com.school.management.school_management_system.service;



import com.school.management.school_management_system.entity.Teacher;
import com.school.management.school_management_system.KafkaProducerConsumer.ProducerService;
import com.school.management.school_management_system.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository repo;
    private final ProducerService producer;

    public TeacherService(TeacherRepository repo, ProducerService producer) { this.repo = repo; this.producer = producer; }

    public Teacher create(Teacher t) {
        Teacher saved = repo.save(t);
        producer.sendEvent("school.events", "teacher.created:" + saved.getId());
        return saved;
    }
    public List<Teacher> findAll() { return repo.findAll(); }
    public Teacher findById(Long id) { return repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found")); }
    public Teacher update(Long id, Teacher t) {
        Teacher existing = findById(id);
        existing.setFirstName(t.getFirstName());
        existing.setLastName(t.getLastName());
        existing.setSubject(t.getSubject());
        Teacher updated = repo.save(existing);
        producer.sendEvent("school.events", "teacher.updated:" + updated.getId());
        return updated;
    }
    public void delete(Long id) { repo.deleteById(id); producer.sendEvent("school.events", "teacher.deleted:" + id); }
}

