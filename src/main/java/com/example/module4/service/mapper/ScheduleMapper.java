package com.example.module4.service.mapper;

import com.example.module4.model.Schedule;
import com.example.module4.model.dto.ScheduleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    ScheduleDto toDto(Schedule schedule);

}
