package com.example.module4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
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
    private Set<Schedule> schedule = new HashSet<>();

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Course)) {
            return false;
        }
        Course course = (Course) other;
        return (course.id == id && course.courseName == courseName
                && course.description == description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }
}
