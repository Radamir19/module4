package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

public record ScheduleDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id, OffsetDateTime dateStart, OffsetDateTime dateEnd, Long groupId, Long courseId){
}
