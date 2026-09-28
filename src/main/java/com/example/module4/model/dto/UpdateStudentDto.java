package com.example.module4.model.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateStudentDto (@NotBlank String name, @NotBlank String surname ) {
}
