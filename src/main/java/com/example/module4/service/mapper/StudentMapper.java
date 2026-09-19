package com.example.module4.service.mapper;

import com.example.module4.model.Student;
import com.example.module4.model.dto.StudentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentDto toDto(Student student);

}
