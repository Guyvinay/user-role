package com.app.rest;

import com.app.dto.UserRoleDto;
import com.app.service.UserRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class UserRoleController {

    private final UserRoleService userRoleService;

    // Create Role
    @PostMapping
    public ResponseEntity<UserRoleDto> createRole(@RequestBody @Valid UserRoleDto dto) {
        UserRoleDto saved = userRoleService.saveRole(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDto> getRole(@PathVariable UUID id) {
        UserRoleDto role = userRoleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<UserRoleDto>> getAllRoles() {
        return ResponseEntity.ok(userRoleService.getAllRoles());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDto> updateRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserRoleDto dto) {

        UserRoleDto updated = userRoleService.updateRole(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable UUID id) {
        String message = userRoleService.deleteRoleById(id);
        return ResponseEntity.ok(message);
    }
}
