package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CourseDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                        @NotBlank String courseName,
                        String description,
                        @NotNull @Positive Long teacherId){
}
