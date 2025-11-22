package com.app.repo;

import com.app.model.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRoleMappingRepository extends JpaRepository<UserRoleMapping, UUID> {
    Optional<UserRoleMapping> findByUserIdAndRoleId(UUID userId, UUID roleId);
    List<UserRoleMapping> findAllByUserId(UUID userId);
    List<UserRoleMapping> findAllByRoleId(UUID roleId);
    void deleteAllByRoleId(UUID roleId);
    void deleteAllByUserId(UUID roleId);
}
