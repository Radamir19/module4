package com.example.module4.controller;

import com.example.module4.model.dto.StudentDto;
import com.example.module4.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentDto>> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return ResponseEntity.ok(studentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudent(@PathVariable("id") Long id) {
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long id, @RequestBody StudentDto dto) {
        return ResponseEntity.ok(studentService.updateStudent(id, dto));
    }

    @PostMapping("/{studentId}/groups/{groupId}")
    public ResponseEntity<StudentDto> addToGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        return ResponseEntity.ok(studentService.addStudentToGroup(groupId, studentId));
    }

    @DeleteMapping("/{studentId}/groups/{groupId}")
    public ResponseEntity<Void> removeFromGroup(@PathVariable Long studentId, @PathVariable Long groupId) {
        studentService.removeStudentFromGroup(groupId, studentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
