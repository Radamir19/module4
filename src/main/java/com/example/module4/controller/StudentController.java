package com.example.module4.controller;

import com.example.module4.model.dto.StudentDto;
import com.example.module4.service.StudentService;
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
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Управление студентом")
public class StudentController {
    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Поиск всех студентов")
    public ResponseEntity<Page<StudentDto>> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return ResponseEntity.ok(studentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск студента")
    public ResponseEntity<StudentDto> getStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение студента")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    @PutMapping("/{studentId}/groups/{groupId}")
    @Operation(summary = "Добавление студента в группу")
    public ResponseEntity<StudentDto> addToGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.addStudentToGroup(groupId, studentId));
    }

    @DeleteMapping("/{studentId}/groups/{groupId}")
    @Operation(summary = "Удаление студента из группы")
    public ResponseEntity<Void> removeFromGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        studentService.removeStudentFromGroup(groupId, studentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
