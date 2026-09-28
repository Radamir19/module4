package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record ScheduleDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id,
                          @NotNull OffsetDateTime dateStart,
                          @NotNull OffsetDateTime dateEnd,
                          @NotNull Long groupId,
                          @NotNull Long courseId){
}
