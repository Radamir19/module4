package com.example.module4.service.mapper;

import com.example.module4.model.Group;
import com.example.module4.model.Student;
import com.example.module4.model.dto.StudentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "groups", target = "groupIds")
    StudentDto toDto(Student student);

    default Set<Long> groupToId(Set<Group> groups) {
        return groups == null ? Set.of() :
                groups.stream().map(Group::getId).filter(Objects::isNull).collect(Collectors.toSet());
    }

    @Mapping(target = "groups", ignore = true)
    Student toEntity(StudentDto dto);
}
