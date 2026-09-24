package com.example.module4.repository;

import com.example.module4.model.Student;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s.id FROM Student s JOIN s.groups g WHERE g.id = :groupId AND SIZE(s.groups) = 1")
    List<Long> findStudentsOnlyInOneGroup(@Param("groupId") Long groupId);

    @Query("SELECT s.id FROM Student s")
    Page<Long> findPageOfIds(Pageable pageable);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.groups WHERE s.id IN :ids")
    List<Student> findAllWithGroupsByIdIn(@Param("ids") List<Long> ids);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Student s WHERE s.id = :id")
    Optional<Student> findByIdForUpdate(Long id);
}
