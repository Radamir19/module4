package com.example.module4.repository;

import com.example.module4.model.Teacher;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    @Override
    @EntityGraph(attributePaths = {"course"})
    Page<Teacher> findAll(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Teacher> findLockedById(Long id);
}
