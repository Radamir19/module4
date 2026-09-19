package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.exception.ValidateException;
import com.example.module4.model.*;
import com.example.module4.model.dto.ScheduleDto;
import com.example.module4.repository.*;
import com.example.module4.service.mapper.ScheduleMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, ScheduleMapper scheduleMapper, GroupRepository groupRepository, CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleMapper = scheduleMapper;
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public Page<ScheduleDto> getAll(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(scheduleMapper::toDto);
    }

    public ScheduleDto getSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Занятие с таким id не найдено."));
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public ScheduleDto updateSchedule(Long scheduleId, ScheduleDto dto) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Занятие с таким id не найдено."));
        scheduleHelper(schedule.getGroup().getId(), schedule.getTeacher().getId(), dto.dateStart(), dto.dateEnd(), schedule.getId());
        schedule.setDateStart(dto.dateStart());
        schedule.setDateEnd(dto.dateEnd());
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public ScheduleDto createSchedule(ScheduleDto dto) {
        Schedule schedule = new Schedule();
        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new NotFoundException("Курс с таким id не найден."));
        Teacher teacher = course.getTeacher();
        Group group = groupRepository.findById(dto.groupId())
                .orElseThrow(() -> new NotFoundException("Группа с таким id не найдена"));
        scheduleHelper(dto.groupId(), teacher.getId(), dto.dateStart(), dto.dateEnd(), -1L);
        schedule.setDateStart(dto.dateStart());
        schedule.setDateEnd(dto.dateEnd());
        schedule.setCourse(course);
        schedule.setTeacher(teacher);
        schedule.setGroup(group);
        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    public Page<ScheduleDto> getScheduleForTeacher(Long teacherId, Pageable pageable) {
        if(!teacherRepository.existsById(teacherId)) {
            throw new NotFoundException("Учителя с таким id не существует.");
        }
        return scheduleRepository.findAllByTeacherId(teacherId, pageable)
                .map(scheduleMapper::toDto);
    }

    public Page<ScheduleDto> getScheduleForGroup(Long groupId, Pageable pageable) {
        if(!groupRepository.existsById(groupId)) {
            throw new NotFoundException("Группы с таким id не существует.");
        }
        return scheduleRepository.findAllByGroupId(groupId, pageable)
                .map(scheduleMapper::toDto);
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new NotFoundException("Занятие с таким id не найдено."));
        scheduleRepository.delete(schedule);
    }

    private void scheduleHelper(Long groupId, Long teacherId, LocalDateTime dateStart, LocalDateTime dateEnd, Long id) {
        if(dateStart.isAfter(dateEnd)) {
            throw new ValidateException("Дата начала занятия не может быть позже её окончания.");
        }else if(dateStart.isEqual(dateEnd)) {
            throw new ValidateException("Дата начала занятия не может совпадать с её окончанием.");
        }
        boolean overlapping = scheduleRepository.existsOverlapping(groupId, teacherId, dateStart, dateEnd, id);
        if(overlapping) {
            throw new ValidateException("На это время у группы или преподавателя уже назначено занятие.");
        }
    }
}
