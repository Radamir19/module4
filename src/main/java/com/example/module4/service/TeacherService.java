package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.model.Teacher;
import com.example.module4.model.dto.TeacherDto;
import com.example.module4.repository.TeacherRepository;
import com.example.module4.service.mapper.TeacherMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public Page<TeacherDto> getAll(Pageable pageable) {
        return teacherRepository.findAll(pageable).map(teacherMapper::toDto);
    }

    @Transactional
    public TeacherDto createTeacher(TeacherDto dto) {
        Teacher teacher = teacherMapper.toEntity(dto);
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    public TeacherDto getTeacher(Long id) {
        return teacherMapper.toDto(teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден.")));
    }

    @Transactional
    public TeacherDto updateTeacher(Long id, TeacherDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден."));
        teacher.setName(dto.name());
        teacher.setSurname(dto.surname());
        teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        if(!teacherRepository.existsById(id)) {
            throw new NotFoundException("Учитель с таким id не найден.");
        }
        teacherRepository.deleteById(id);
    }
}
