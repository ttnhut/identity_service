package com.nhut.identity_service.payload.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateDTO {

    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
