package com.example.module4.repository;

import com.example.module4.model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @EntityGraph(attributePaths = {"course", "group"})
    Page<Schedule> findAllByCourseTeacherId(Long teacherId, Pageable pageable);
    @EntityGraph(attributePaths = {"course", "group"})
    Page<Schedule> findAllByGroupId(Long groupId, Pageable pageable);

    @Query("SELECT COUNT(s) > 0 FROM Schedule s WHERE :dateStart < s.dateEnd AND :dateEnd > s.dateStart AND (s.group.id = :groupId OR s.course.teacher.id = :teacherId) AND (:id IS NULL OR s.id <> :id)")
    boolean existsOverlapping(@Param("groupId") Long groupId,
                              @Param("teacherId") Long teacherId,
                              @Param("dateStart") OffsetDateTime dateStart,
                              @Param("dateEnd") OffsetDateTime dateEnd,
                              @Param("id") Long id);
    @Query("DELETE FROM Schedule s WHERE s.dateEnd < :border")
    @Modifying
    void deleteSchedules(@Param("border") OffsetDateTime border);
}
