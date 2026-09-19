package com.example.module4.model.dto;

import java.util.Set;

public record StudentDto (Long id, String name, String surname, Set<Long> groupIds){
}
