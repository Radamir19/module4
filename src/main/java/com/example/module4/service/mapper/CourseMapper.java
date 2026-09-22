package com.example.module4.service.mapper;

import com.example.module4.model.Course;
import com.example.module4.model.dto.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source = "teacher.id", target = "teacherId")
    CourseDto toDto(Course course);

    Course toEntity(CourseDto dto);
}
