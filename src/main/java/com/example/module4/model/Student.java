package com.example.module4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "student")
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name")
    private String name;

    @Column(name = "student_surname")
    private String surname;

    @NotEmpty
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_group",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<Group> groups = new HashSet<>();

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Student)) {
            return false;
        }
        Student s = (Student) other;
        return (s.id == id && s.name == name && s.surname == surname);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
