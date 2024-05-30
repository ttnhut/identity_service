package com.nhut.identity_service.mapper;

import com.nhut.identity_service.entities.User;
import com.nhut.identity_service.payload.request.UserCreateDTO;
import com.nhut.identity_service.payload.request.UserUpdateDTO;
import com.nhut.identity_service.payload.response.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateDTO userCreateDTO);
    UserResponseDTO toUserResponseDTO(User user);
    List<UserResponseDTO> toUserResponseDTOList(List<User> users);
    void updateUser(@MappingTarget User user, UserUpdateDTO userUpdateDTO);
}
