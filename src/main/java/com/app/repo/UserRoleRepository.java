package com.app.repo;

import com.app.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository  extends JpaRepository<UserRole, UUID> {
    boolean existsByName(String roleName);
}
