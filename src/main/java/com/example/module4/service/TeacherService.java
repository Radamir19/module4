package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.model.Teacher;
import com.example.module4.model.dto.TeacherDto;
import com.example.module4.repository.TeacherRepository;
import com.example.module4.service.mapper.TeacherMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherService(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    public Page<TeacherDto> getAll(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(teacherMapper::toDto);
    }

    public TeacherDto createTeacher(TeacherDto dto) {
        Teacher teacher = new Teacher();
        teacher.setName(dto.name());
        teacher.setSurname(dto.surname());
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public TeacherDto getTeacher(Long id) {
        return teacherMapper.toDto(teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден.")));
    }

    public TeacherDto updateTeacher(Long id, TeacherDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден."));
        teacher.setName(dto.name());
        teacher.setSurname(dto.surname());
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.delete(teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден.")));
    }
}
