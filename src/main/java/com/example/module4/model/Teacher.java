package com.example.module4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "teacher_name", nullable = false)
    private String name;

    @Column(name = "teacher_surname", nullable = false)
    private String surname;

    @OneToOne(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Course course;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    private Set<Schedule> schedule;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setSchedule(Set<Schedule> schedule) {
        this.schedule = schedule;
    }
}
