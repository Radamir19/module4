package com.example.module4.service.mapper;

import com.example.module4.model.Group;
import com.example.module4.model.dto.GroupDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDto toDto(Group group);
}
