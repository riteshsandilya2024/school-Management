package com.school.management.school_management_system.AuditPresistance;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditEvent, Long> {}

