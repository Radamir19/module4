package com.example.module4.service.mapper;

import com.example.module4.model.Course;
import com.example.module4.model.Teacher;
import com.example.module4.model.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "teacher.id", target = "teacherId")
    CourseDto toDto(Course course);

    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    Course toEntity(CourseDto dto);
}
