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
@Table(name = "groups")
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_name", unique = true, nullable = false)
    private String groupName;

    @ManyToMany(mappedBy = "groups")
    private Set<Student> students;

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE)
    private Set<Schedule> schedule = new HashSet<>();

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Group)) {
            return false;
        }
        Group group = (Group) other;
        return (group.id == id && group.groupName == groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }
}
