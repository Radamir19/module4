package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CourseDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id, String courseName, String description, Long teacherId){
}
