package com.example.module4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "class")
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "class_name")
    private String groupName;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private Set<Schedule> schedule;

}
