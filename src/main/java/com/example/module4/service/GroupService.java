package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.model.Group;
import com.example.module4.model.Schedule;
import com.example.module4.model.Student;
import com.example.module4.model.dto.GroupDto;
import com.example.module4.model.dto.ScheduleDto;
import com.example.module4.repository.GroupRepository;
import com.example.module4.repository.ScheduleRepository;
import com.example.module4.repository.StudentRepository;
import com.example.module4.service.mapper.GroupMapper;
import com.example.module4.service.mapper.ScheduleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final GroupMapper groupMapper;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public Page<GroupDto> getAll(Pageable pageable) {
        Page<Long> ids = groupRepository.findPageOfIds(pageable);
        Map<Long, Group> byId = groupRepository.findAllWithStudentsByIdIn(ids.getContent())
                .stream()
                .collect(Collectors.toMap(Group::getId, Function.identity()));
        return ids.map(id -> groupMapper.toDto(byId.get(id)));
    }

    public GroupDto getGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа с таким id не найдена."));
        return groupMapper.toDto(group);
    }

    @Transactional
    public GroupDto createGroup(GroupDto dto) {
        Group group = new Group();
        group.setGroupName(dto.name());
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    @Transactional
    public GroupDto updateGroup(Long groupId, GroupDto dto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа с таким id не найдена."));
        group.setGroupName(dto.name());
        return groupMapper.toDto(group);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа с таким id не найдена."));
        studentRepository.deleteAll(studentRepository.findStudentsOnlyInOneGroup(groupId));
        groupRepository.delete(group);
    }

    public Page<ScheduleDto> getScheduleForGroup(Long groupId, Pageable pageable) {
        return scheduleRepository.findAllByGroupId(groupId, pageable).map(scheduleMapper::toDto);
    }
}
