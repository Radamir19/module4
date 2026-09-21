package com.example.module4.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record GroupDto(@Schema(accessMode = Schema.AccessMode.READ_ONLY) Long id, String name, Set<Long> studentIds) {
}
