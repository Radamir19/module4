package com.example.module4.repository;

import com.example.module4.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @EntityGraph(attributePaths = {"teacher"})
    Page<Course> findAllWithTeachers(Pageable pageable);

    boolean existsByTeacherId(Long teacherId);
    Optional<Course> findByTeacherId(Long teacherId);

}
