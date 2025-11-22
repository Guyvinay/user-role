package com.app.service;

import com.app.dto.UserProfileDto;
import com.app.exception.CustomException;
import com.app.model.UserProfile;
import com.app.repo.UserProfileRepository;
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
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userRepo;
    private final UserRoleMappingService roleMappingService;

    @Override
    public UserProfileDto saveUser(UserProfileDto userDto) {

        // Validate if user already exists if it does throw exception.
        if (userRepo.existsByEmail(userDto.getEmail()))
            throw new CustomException("Email already exists: " + userDto.getEmail());

        log.info("User to be saved {}", userDto.getEmail());
        UserProfile saved = userRepo.save(toEntity(userDto));
        log.info("User saved successfully {}", userDto.getEmail());
        return toDto(saved);
    }

    @Override
    public UserProfileDto getUserById(UUID id) {
        log.info("Getting user with id: {}", id);
        UserProfile user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User not found: " + id));
        log.info("User found with id: {}", id);
        return toDto(user);
    }

    @Override
    public List<UserProfileDto> getAllUsers() {

         List<UserProfile> users =  userRepo.findAll();
         log.info("Users found {}", users.size());

         return users.stream()
                 .map(this::toDto)
                 .collect(Collectors.toList());
    }

    @Override
    public UserProfileDto updateUser(UUID id, UserProfileDto dto) {
        UserProfile user = userRepo.findById(id)
                .orElseThrow(() -> new CustomException("User not found: " + id));

        user.setName(dto.getName());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());

        log.info("User to be updated: {}", user.getEmail());

        UserProfile updatedUser = userRepo.save(user);

        log.info("User updated: {}", updatedUser.getEmail());

        return toDto(updatedUser);
    }

    @Override
    @Transactional
    public String deleteUser(UUID id) {
        if (!userRepo.existsById(id))
            throw new CustomException("User not found: " + id);

        userRepo.deleteById(id);
        log.info("User deleted for id: {}", id);
        roleMappingService.deleteAllMappingByUserId(id);
        log.info("All mappings deleted associated with user id: {}", id);
        return "User deleted: " + id;
    }

    public UserProfile toEntity(UserProfileDto dto) {
        UserProfile user = new UserProfile();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        user.setPhone(dto.getPhone());
        return user;
    }

    public UserProfileDto toDto(UserProfile entity) {
        UserProfileDto dto = new UserProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setPhone(entity.getPhone());
        return dto;
    }
}
