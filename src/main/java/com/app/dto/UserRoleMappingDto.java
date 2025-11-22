package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleMappingDto {
    private UUID id;
    private UUID userId;
    private UUID roleId;
    private long roleAssigned;
}
