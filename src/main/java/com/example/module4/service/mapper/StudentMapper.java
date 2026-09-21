package com.example.module4.service.mapper;

import com.example.module4.model.Group;
import com.example.module4.model.Student;
import com.example.module4.model.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "groups", target = "groupIds")
    StudentDto toDto(Student student);

    default Long groupToId(Group group) {
        return group.getId();
    }
}
