package com.app.service;

import com.app.dto.UserRoleDto;
import com.app.model.UserRole;
import com.app.repo.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository roleRepo;
    private final UserRoleMappingService roleMappingService;

    @Override
    public UserRoleDto saveRole(UserRoleDto dto) {
        if (roleRepo.existsByName(dto.getName()))
            throw new RuntimeException("Role already exists: " + dto.getName());
        log.info("Saving role: {}", dto.getName());
        dto.setCreatedAt(System.currentTimeMillis());
        UserRole entity = toEntity(dto);

        UserRole saved = roleRepo.save(entity);
        log.info("Role saved role: id: {}, {}", saved.getName(), saved.getId());
        return toDto(saved);
    }

    @Override
    public UserRoleDto getRoleById(UUID roleId) {
        log.info("Getting role woth id: {}", roleId);
        UserRole role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));
        log.info("Role found returning role: {}, id: {}", role.getName(), role.getId());
        return toDto(role);
    }

    @Override
    public List<UserRoleDto> getAllRoles() {
        List<UserRole> roles = roleRepo.findAll();
        log.info("Roles found: {}", roles.size());
        return roles.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleDto updateRole(UUID roleId, UserRoleDto dto) {

        log.info("Updating role with id: {}", roleId);

        UserRole role = roleRepo.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleId));

        log.info("Role found to be update: {}", roleId);

        role.setName(dto.getName());
        role.setStatus(dto.getStatus());

        UserRole updatedRole = roleRepo.save(role);

        log.info("Role updated roleId: {}", role);

        return toDto(updatedRole);
    }

    @Override
    @Transactional
    public String deleteRoleById(UUID id) {
        log.info("Role to be deleted by id: {}", id);
        if (!roleRepo.existsById(id))
            throw new RuntimeException("Role not found: " + id);

        roleRepo.deleteById(id);
        log.info("Role deleted id: {}", id);
        roleMappingService.deleteAllMappingByRoleId(id);
        log.info("All mappings deleted associated with role id: {}", id);
        return "Role deleted: " + id;
    }

    public UserRole toEntity(UserRoleDto dto) {
        UserRole entity = new UserRole();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setStatus(dto.getStatus());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    public UserRoleDto toDto(UserRole entity) {
        UserRoleDto dto = new UserRoleDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
