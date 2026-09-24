package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.exception.ValidateException;
import com.example.module4.model.Course;
import com.example.module4.model.Teacher;
import com.example.module4.model.dto.CourseDto;
import com.example.module4.repository.CourseRepository;
import com.example.module4.repository.TeacherRepository;
import com.example.module4.service.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    public Page<CourseDto> getAll(Pageable pageable) {
        Page<CourseDto> courses = courseRepository.findAll(pageable)
                .map(courseMapper::toDto);
        return courses;
    }

    @Transactional
    public CourseDto createCourse(CourseDto dto) {
        Teacher teacher = teacherRepository.findLockedById(dto.teacherId())
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден."));
        if (courseRepository.existsByTeacherId(dto.teacherId())) {
            throw new ValidateException("Преподаватель уже ведёт другой курс.");
        }
        Course course = courseMapper.toEntity(dto);
        course.setTeacher(teacher);
        Course created = courseRepository.save(course);
        return courseMapper.toDto(created);
    }

    public CourseDto getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с таким id не найден."));
        return courseMapper.toDto(course);
    }

    @Transactional
    public CourseDto updateCourse(Long id, CourseDto dto) {
        Teacher teacher = teacherRepository.findLockedById(dto.teacherId())
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден."));
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с таким id не найден."));
        if (teacher.getCourse() != null) {
            throw new ValidateException("Учитель с таким id уже ведёт курс.");
        }
        course.setCourseName(dto.courseName());
        course.setDescription(dto.description());
        course.setTeacher(teacher);
        return courseMapper.toDto(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        if(!courseRepository.existsById(id)) {
            throw new NotFoundException("Курс с таким id не найден.");
        }
        courseRepository.deleteById(id);
    }

}
