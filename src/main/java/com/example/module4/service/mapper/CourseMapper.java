package com.example.module4.service.mapper;

import com.example.module4.model.Course;
import com.example.module4.model.dto.CourseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseDto toDto(Course course);
}
