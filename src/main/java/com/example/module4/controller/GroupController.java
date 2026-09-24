package com.example.module4.controller;

import com.example.module4.model.dto.GroupDto;
import com.example.module4.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Group", description = "Управление группами")
@Validated
public class GroupController {
    private final GroupService groupService;

    @GetMapping
    @Operation(summary = "Поиск всех групп")
    public ResponseEntity<Page<GroupDto>> findAllGroups(@RequestParam(defaultValue = "0") @PositiveOrZero int page,
                                                        @RequestParam(defaultValue = "20") @Positive int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("groupName").ascending());
        return ResponseEntity.ok(groupService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поиск группы")
    public ResponseEntity<GroupDto> findGroup(@PathVariable("id") @Positive Long id) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @PostMapping()
    @Operation(summary = "Создание группы")
    public ResponseEntity<GroupDto> createGroup(@Valid @RequestBody GroupDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение группы")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("id") @Positive Long id, @Valid @RequestBody GroupDto dto) {
        return ResponseEntity.ok(groupService.updateGroup(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление группы")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") @Positive Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
