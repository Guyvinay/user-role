package com.app.rest;

import com.app.dto.UserProfileDto;
import com.app.service.UserProfileService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileDto> createUser(@RequestBody @Valid UserProfileDto dto) {
        UserProfileDto savedUser = userProfileService.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUser(@PathVariable UUID id) {
        UserProfileDto user = userProfileService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileDto>> getAllUsers() {
        return ResponseEntity.ok(userProfileService.getAllUsers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileDto> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserProfileDto dto) {

        UserProfileDto updated = userProfileService.updateUser(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        String message = userProfileService.deleteUser(id);
        return ResponseEntity.ok(message);
    }
}
