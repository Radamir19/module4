package com.example.module4.model.dto;

import java.time.LocalDateTime;

public record ScheduleDto(Long id, LocalDateTime dateStart, LocalDateTime dateEnd, Long groupId, Long courseId){
}
