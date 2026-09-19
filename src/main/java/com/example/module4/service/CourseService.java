package com.example.module4.service;

import com.example.module4.exception.NotFoundException;
import com.example.module4.model.Course;
import com.example.module4.model.Teacher;
import com.example.module4.model.dto.CourseDto;
import com.example.module4.repository.CourseRepository;
import com.example.module4.repository.TeacherRepository;
import com.example.module4.service.mapper.CourseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, TeacherRepository teacherRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.courseMapper = courseMapper;
    }

    public Page<CourseDto> getAll(Pageable pageable) {
        Page<CourseDto> courses = courseRepository.findAll(pageable)
                .map(courseMapper::toDto);
        return courses;
    }

    public CourseDto createCourse(CourseDto dto) {
        Teacher teacher = teacherRepository.findById(dto.teacherId())
                .orElseThrow(() -> new NotFoundException("Учитель с таким id не найден."));
        Course course = new Course();
        course.setCourseName(dto.courseName());
        course.setDescription(dto.description());
        course.setTeacher(teacher);
        teacher.setCourse(course);
        courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    public CourseDto getCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с таким id не найден."));
        return courseMapper.toDto(course);
    }

    public CourseDto updateCourse(Long id, CourseDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с таким id не найден."));
        course.setCourseName(dto.courseName());
        course.setDescription(dto.description());
        return courseMapper.toDto(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс с таким id не найден."));
        courseRepository.delete(course);
    }

}
