package com.example.module4.controller;

import com.example.module4.model.dto.CourseDto;
import com.example.module4.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Course", description = "Управление курсами")
@Validated
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "Поиск всех курсов")
    public ResponseEntity<Page<CourseDto>> findAllCourses(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                          @RequestParam(defaultValue = "20") @Positive int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("courseName").ascending());
        return ResponseEntity.ok(courseService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск курса")
    public ResponseEntity<CourseDto> findCourse(@PathVariable("id") @Positive Long id) {
        return ResponseEntity.ok(courseService.getCourse(id));
    }

    @PostMapping
    @Operation(summary = "Создание курса")
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение курса")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("id") @Positive Long id, @Valid @RequestBody CourseDto dto) {
        return ResponseEntity.ok(courseService.updateCourse(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление курса")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") @Positive Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
