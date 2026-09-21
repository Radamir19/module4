package com.example.module4.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ScheduleDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id, LocalDateTime dateStart, LocalDateTime dateEnd, Long groupId, Long courseId){
}
