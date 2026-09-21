package com.example.module4.service.mapper;

import com.example.module4.model.Group;
import com.example.module4.model.Student;
import com.example.module4.model.dto.GroupDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "groupName", target = "name")
    @Mapping(source = "students", target = "studentIds")
    GroupDto toDto(Group group);

    default Long studentToId(Student student) {
        return student.getId();
    }
}
