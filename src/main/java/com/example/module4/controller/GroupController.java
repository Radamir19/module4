package com.example.module4.controller;

import com.example.module4.model.dto.GroupDto;
import com.example.module4.service.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public ResponseEntity<Page<GroupDto>> findAllGroups(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("groupName").ascending());
        return ResponseEntity.ok(groupService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDto> findGroup(@PathVariable("id") Long id) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @PostMapping()
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("id") Long id, @RequestBody GroupDto dto) {
        return ResponseEntity.ok(groupService.updateGroup(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("id") Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
