package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.exception.ValidateException;
import com.example.module4.model.Group;
import com.example.module4.model.Student;
import com.example.module4.model.dto.StudentDto;
import com.example.module4.repository.GroupRepository;
import com.example.module4.repository.StudentRepository;
import com.example.module4.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    @Transactional(readOnly = true)
    public Page<StudentDto> getAll(Pageable pageable) {
        Page<Long> ids = studentRepository.findPageOfIds(pageable);
        Map<Long, Student> byId = studentRepository.findAllWithGroupsByIdIn(ids.getContent())
                .stream()
                .collect(Collectors.toMap(Student::getId, Function.identity()));
        return ids.map(id -> studentMapper.toDto(byId.get(id)));
    }

    public StudentDto getStudent(Long id) {
        return studentMapper.toDto(studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Студент с таким id не найден.")));
    }

    @Transactional
    public StudentDto createStudent(StudentDto dto) {
        List<Group> groups = groupRepository.findAllById(dto.groupIds());
        if(groups.size() != dto.groupIds().size()) {
            throw new NotFoundException("Одна или несколько групп не найдены.");
        }

        Student student = new Student();
        student.setName(dto.name());
        student.setSurname(dto.surname());
        student.setGroups(new HashSet<>(groups));
        studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public StudentDto updateStudent(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Студент не найден"));
        student.setName(dto.name());
        student.setSurname(dto.surname());
        return studentMapper.toDto(student);
    }

    @Transactional
    public StudentDto addStudentToGroup(Long groupId, Long studentId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа не найдена."));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Студент не найден."));
        student.getGroups().add(group);
        return studentMapper.toDto(student);
    }

    @Transactional
    public void removeStudentFromGroup(Long groupId, Long studentId) {
        Student student = studentRepository.findByIdForUpdate(studentId)
                .orElseThrow(() -> new NotFoundException("Студент не найден."));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Группа не найдена."));
        if(!student.getGroups().contains(group)) {
            throw new ValidateException("Студент не учится в группе с таким id");
        }else if(student.getGroups().size() == 1) {
            throw new ValidateException("Студент должен находиться хотя бы в одной группе");
        }else {
            student.getGroups().remove(group);
        }
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new NotFoundException("Студент с таким id не найден.");
        }
        studentRepository.deleteById(studentId);
    }
}