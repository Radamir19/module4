package com.example.module4.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.Set;

public record GroupDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                       @NotBlank String name,
                       @Schema(accessMode = Schema.AccessMode.READ_ONLY) Set<@Positive Long> studentIds) {
}
