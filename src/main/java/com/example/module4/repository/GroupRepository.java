package com.example.module4.repository;

import com.example.module4.model.Group;
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
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Query("SELECT g.id FROM Group g")
    Page<Long> findPageOfIds(Pageable pageable);

    @Query("SELECT g FROM Group g LEFT JOIN FETCH g.students WHERE g.id IN :ids")
    List<Group> findAllWithStudentsByIdIn(@Param("ids") List<Long> ids);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Group> findLockedById(Long id);
}
