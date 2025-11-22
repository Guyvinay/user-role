package com.app.service;

import com.app.dto.UserProfileDto;

import java.util.List;
import java.util.UUID;

public interface UserProfileService {
    UserProfileDto saveUser(UserProfileDto user);
    UserProfileDto getUserById(UUID id);
    List<UserProfileDto> getAllUsers();
    UserProfileDto updateUser(UUID id, UserProfileDto user);
    String deleteUser(UUID id);
}
