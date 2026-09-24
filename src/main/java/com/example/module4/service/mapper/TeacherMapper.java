package com.example.module4.service.mapper;

import com.example.module4.model.Teacher;
import com.example.module4.model.dto.TeacherDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDto toDto(Teacher teacher);
    Teacher toEntity(TeacherDto dto);
}
