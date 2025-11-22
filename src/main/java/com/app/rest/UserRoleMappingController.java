package com.app.rest;

import com.app.dto.UserRoleMappingDto;
import com.app.service.UserRoleMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/role-mapping")
@RequiredArgsConstructor
public class UserRoleMappingController {

    private final UserRoleMappingService mappingService;

    @PostMapping("/assign")
    public ResponseEntity<UserRoleMappingDto> assignRoleToUser(
            @RequestParam UUID roleId,
            @RequestParam UUID userId
    ) {

        UserRoleMappingDto result = mappingService.assignRoleToUser(roleId, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @DeleteMapping("/revoke")
    public ResponseEntity<String> revokeRoleFromUser(
            @RequestParam UUID roleId,
            @RequestParam UUID userId) {

        String message = mappingService.revokeRoleFromUser(roleId, userId);
        return ResponseEntity.ok(message);
    }

    @GetMapping()
    public ResponseEntity<List<UserRoleMappingDto>> getAllMappings() {
        List<UserRoleMappingDto> result = mappingService.getAllRoleMappings();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRoleMappingDto>> getRolesForUser(
            @PathVariable UUID userId) {

        List<UserRoleMappingDto> roles = mappingService.getAllRolesForUser(userId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<UserRoleMappingDto>> getUsersForRole(
            @PathVariable UUID roleId) {

        List<UserRoleMappingDto> users = mappingService.getAllUserForARole(roleId);
        return ResponseEntity.ok(users);
    }
}
