package com.example.module4.service.mapper;

import com.example.module4.model.Schedule;
import com.example.module4.model.dto.ScheduleDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "group.id", target = "groupId")
    ScheduleDto toDto(Schedule schedule);

}
