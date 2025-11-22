package com.app.service;

import com.app.dto.UserRoleMappingDto;

import java.util.List;
import java.util.UUID;

public interface UserRoleMappingService {
    UserRoleMappingDto assignRoleToUser(UUID roleId, UUID userId);
    List<UserRoleMappingDto> getAllRoleMappings();
    String revokeRoleFromUser(UUID roleId, UUID userId);
    List<UserRoleMappingDto> getAllRolesForUser(UUID userId);
    List<UserRoleMappingDto> getAllUserForARole(UUID roleId);
    String deleteAllMappingByRoleId(UUID roleId);
    String deleteAllMappingByUserId(UUID userId);
}
