package com.example.module4.repository;

import com.example.module4.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, CrudRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN FETCH s.groups g WHERE g.id = :groupId GROUP BY s HAVING COUNT(g) = 1")
    List<Student> findStudentsOnlyInOneGroup(@Param("groupId") Long groupId);
}
