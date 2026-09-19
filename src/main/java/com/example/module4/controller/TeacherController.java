package com.example.module4.controller;

import com.example.module4.model.dto.TeacherDto;
import com.example.module4.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/teachers")
@Tag(name = "Teacher", description = "Управление учителями")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @Operation(summary = "Поиск всех учителей")
    public ResponseEntity<Page<TeacherDto>> findAllTeachers(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return ResponseEntity.ok(teacherService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск учителя по id")
    public ResponseEntity<TeacherDto> getTeacher(@PathVariable("id") Long id) {
        return ResponseEntity.ok(teacherService.getTeacher(id));
    }

    @PostMapping
    @Operation(summary = "Создание нового учителя")
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createTeacher(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных об учителе")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable("id") Long id, @RequestBody TeacherDto dto) {
        return ResponseEntity.ok(teacherService.updateTeacher(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление учителя, вместе с ним курса и расписания")
    public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacher(id);
        return ResponseEntity.noContent().build();
    }
}
