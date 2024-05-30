package com.nhut.identity_service.services;

import com.nhut.identity_service.payload.request.UserCreateDTO;
import com.nhut.identity_service.payload.request.UserUpdateDTO;
import com.nhut.identity_service.payload.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    /**
     * Create user
     * @param userCreateDTO userCreateDTO
     * @return UserResponseDTO
     */
    UserResponseDTO createUser(UserCreateDTO userCreateDTO);

    /**
     * Update user
     * @param userUpdateDTO userUpdateDTO
     * @param id userId
     * @return UserResponseDTO
     */
    UserResponseDTO updateUser(UserUpdateDTO userUpdateDTO, String id);

    /**
     * Get single user
     * @param id userId
     * @return UserResponseDTO
     */
    UserResponseDTO getSingleUser(String id);

    /**
     * Get all users
     * @return List<UserResponseDTO>
     */
    List<UserResponseDTO> getAllUsers();

    /**
     * Delete user
     * @param id userId
     */
    void deleteUser(String id);

}
