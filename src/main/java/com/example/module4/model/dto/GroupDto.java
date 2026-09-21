package com.example.module4.model.dto;


import java.util.Set;

public record GroupDto(Long id, String name, Set<Long> studentIds) {
}
