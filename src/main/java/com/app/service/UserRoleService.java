package com.app.service;

import com.app.dto.UserRoleDto;

import java.util.List;
import java.util.UUID;

public interface UserRoleService {
    UserRoleDto saveRole(UserRoleDto roleDto);
    UserRoleDto getRoleById(UUID roleId);
    List<UserRoleDto> getAllRoles();
    UserRoleDto updateRole(UUID roleId, UserRoleDto roleDto);
    String deleteRoleById(UUID id);
}
