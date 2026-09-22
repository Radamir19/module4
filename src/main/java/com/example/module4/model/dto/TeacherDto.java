package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

public record TeacherDto (@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                          @NotEmpty String name, @NotEmpty String surname){
}
