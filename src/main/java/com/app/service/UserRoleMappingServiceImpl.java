package com.app.service;

import com.app.dto.UserRoleMappingDto;
import com.app.model.UserRoleMapping;
import com.app.repo.UserProfileRepository;
import com.app.repo.UserRoleMappingRepository;
import com.app.repo.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRoleMappingServiceImpl implements UserRoleMappingService{

    private final UserProfileRepository userRepo;
    private final UserRoleRepository roleRepo;
    private final UserRoleMappingRepository mappingRepo;

    @Override
    public UserRoleMappingDto assignRoleToUser(UUID roleId, UUID userId) {
        log.info("Assigning role: {} to user: {}", roleId, userId);
        if (!userRepo.existsById(userId))
            throw new RuntimeException("User not found: " + userId);

        if (!roleRepo.existsById(roleId))
            throw new RuntimeException("Role not found: " + roleId);

        Optional<UserRoleMapping> mapping = mappingRepo.findByUserIdAndRoleId(userId, roleId);
        log.info("Mapping not found for user: {} role: {}, we can assign role", userId, roleId);
        if (mapping.isPresent())
            throw new RuntimeException("Role already assigned to user");

        UserRoleMapping entity = new UserRoleMapping();
        entity.setUserId(userId);
        entity.setRoleId(roleId);
        entity.setRoleAssigned(System.currentTimeMillis());

        UserRoleMapping roleMapping = mappingRepo.save(entity);

        log.info("User role: {} assigned to user: {} with id:{}", roleId, userId, roleMapping.getId());

        return toDto(roleMapping);
    }

    @Override
    public List<UserRoleMappingDto> getAllRoleMappings() {
        List<UserRoleMapping> mappings = mappingRepo.findAll();
        log.info("All rule mappings: {}", mappings.size());

        return mappings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String revokeRoleFromUser(UUID roleId, UUID userId) {
        log.info("Revoking role: {} from user: {}", roleId, userId);

        UserRoleMapping mapping = mappingRepo.findByUserIdAndRoleId(userId, roleId)
                .orElseThrow(() -> new RuntimeException("Mapping not found"));

        mappingRepo.deleteById(mapping.getId());

        log.info("Role: {} revoked from user: {}", roleId, userId);

        return "Role: " + roleId + ", revoked successfully from user" + userId;
    }

    @Override
    public List<UserRoleMappingDto> getAllRolesForUser(UUID userId) {

        log.info("Getting all mappings for this user: {}", userId);
        List<UserRoleMapping> rolesMappings = mappingRepo.findAllByUserId(userId);
        log.info("Found {} mappings for this user: {}", rolesMappings.size(), userId);

        return rolesMappings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRoleMappingDto> getAllUserForARole(UUID roleId) {

        log.info("Getting all mappings for this role: {}", roleId);
        List<UserRoleMapping> rolesMappings = mappingRepo.findAllByRoleId(roleId);
        log.info("Found {} mappings for this role: {}", rolesMappings.size(), roleId);

        return rolesMappings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public String deleteAllMappingByRoleId(UUID roleId) {
        log.info("Deleting all mapping associated with role id {}", roleId);
        mappingRepo.deleteAllByRoleId(roleId);
        return "All mappings  deleted associated with role id" + roleId;
    }

    @Override
    public String deleteAllMappingByUserId(UUID userId) {
        log.info("Deleting all mapping associated with user id {}", userId);
        mappingRepo.deleteAllByUserId(userId);
        return "All mappings  deleted associated with user id" + userId;
    }

    public UserRoleMappingDto toDto(UserRoleMapping roleMapping) {
        UserRoleMappingDto roleMappingDto = new UserRoleMappingDto();
        roleMappingDto.setId(roleMapping.getId());
        roleMappingDto.setRoleId(roleMapping.getRoleId());
        roleMappingDto.setUserId(roleMapping.getUserId());
        roleMappingDto.setRoleAssigned(roleMapping.getRoleAssigned());
        return roleMappingDto;
    }
}
