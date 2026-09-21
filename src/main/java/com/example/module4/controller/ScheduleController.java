package com.example.module4.controller;

import com.example.module4.model.dto.ScheduleDto;
import com.example.module4.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedule", description = "Управление занятиями")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping
    @Operation(summary = "Поиск всех занятий")
    public ResponseEntity<Page<ScheduleDto>> findAllSchedules(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return ResponseEntity.ok(scheduleService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск занятия")
    public ResponseEntity<ScheduleDto> getSchedule(@PathVariable("id") Long id) {
        return ResponseEntity.ok(scheduleService.getSchedule(id));
    }

    @PostMapping
    @Operation(summary = "Создание занятия")
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление занятия")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable("id") Long id, @RequestBody ScheduleDto dto) {
        return ResponseEntity.ok(scheduleService.updateSchedule(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление занятия")
    public ResponseEntity<Void> deleteSchedule(@PathVariable("id") Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(params = "teacherId")
    @Operation(summary = "График занятий для учителя")
    public ResponseEntity<Page<ScheduleDto>> getScheduleForTeacher(@RequestParam Long teacherId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateStart").ascending());
        return ResponseEntity.ok(scheduleService.getScheduleForTeacher(teacherId, pageable));
    }

    @GetMapping(params = "groupId")
    @Operation(summary = "График занятий для группы")
    public ResponseEntity<Page<ScheduleDto>> getScheduleForGroup(@RequestParam Long groupId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("dateStart").ascending());
        return ResponseEntity.ok(scheduleService.getScheduleForGroup(groupId, pageable));
    }
}
