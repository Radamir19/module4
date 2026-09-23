package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record TeacherDto (@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                          @NotBlank String name,
                          @NotBlank String surname){
}
