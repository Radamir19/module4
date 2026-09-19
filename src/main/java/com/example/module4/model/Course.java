package com.example.module4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne
    @JoinColumn(name = "teacher_id", unique = true)
    private Teacher teacher;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private Set<Schedule> schedule;


    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }
}
