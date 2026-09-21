package com.example.module4.repository;

import com.example.module4.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("select c from Course c join fetch c.teacher")
    Page<Course> findAllWithTeachers(Pageable pageable);
}
