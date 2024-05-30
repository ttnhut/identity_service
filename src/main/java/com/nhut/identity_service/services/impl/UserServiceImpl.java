package com.nhut.identity_service.services.impl;

import com.nhut.identity_service.entities.User;
import com.nhut.identity_service.exception.AppException;
import com.nhut.identity_service.exception.ErrorCode;
import com.nhut.identity_service.mapper.UserMapper;
import com.nhut.identity_service.payload.request.UserCreateDTO;
import com.nhut.identity_service.payload.request.UserUpdateDTO;
import com.nhut.identity_service.payload.response.UserResponseDTO;
import com.nhut.identity_service.repositories.UserRepository;
import com.nhut.identity_service.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        if (this.userRepository.existsByUsername(userCreateDTO.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = this.userMapper.toUser(userCreateDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userMapper.toUserResponseDTO(this.userRepository.save(user));
    }

    @Override
    public UserResponseDTO updateUser(UserUpdateDTO userUpdateDTO, String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
        this.userMapper.updateUser(user, userUpdateDTO);
        return this.userMapper.toUserResponseDTO(this.userRepository.save(user));
    }

    @Override
    public UserResponseDTO getSingleUser(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
        return this.userMapper.toUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return this.userMapper.toUserResponseDTOList(users);
    }

    @Override
    public void deleteUser(String id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("Error"));
        this.userRepository.delete(user);
    }
}
