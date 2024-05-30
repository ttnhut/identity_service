package com.nhut.identity_service.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
}
