package com.example.module4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @OneToOne(mappedBy = "teacher", cascade = CascadeType.REMOVE)
    @MapsId
    private Course course;

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Teacher)) {
            return false;
        }
        Teacher t = (Teacher) other;
        return (t.id == id && t.name == name && t.surname == surname);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
