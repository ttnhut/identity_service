package com.nhut.identity_service.controllers;

import com.nhut.identity_service.payload.request.UserCreateDTO;
import com.nhut.identity_service.payload.request.UserUpdateDTO;
import com.nhut.identity_service.payload.response.APIResponse;
import com.nhut.identity_service.payload.response.UserResponseDTO;
import com.nhut.identity_service.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public APIResponse<List<UserResponseDTO>> getAllUsers() {
        return new APIResponse<>(HttpStatus.OK.value(), "Successfully", this.userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public APIResponse<UserResponseDTO> getSingleUser(@PathVariable(value = "id") String id) {
        return new APIResponse<>(HttpStatus.OK.value(), "Successfully", this.userService.getSingleUser(id));
    }

    @PostMapping
    public APIResponse<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return new APIResponse<>(HttpStatus.CREATED.value(), "Successfully", this.userService.createUser(userCreateDTO));
    }

    @PutMapping("/{id}")
    public APIResponse<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable(value = "id") String id) {
        return new APIResponse<>(HttpStatus.OK.value(), "Successfully", this.userService.updateUser(userUpdateDTO, id));
    }

    @DeleteMapping("/{id}")
    public APIResponse<String> deleteUser(@PathVariable(value = "id") String id) {
        this.userService.deleteUser(id);
        return new APIResponse<>(HttpStatus.OK.value(), "Successfully", "User is delete with id: " + id);
    }

}
