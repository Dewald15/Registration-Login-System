package com.dee.registration_login_system.dto;

import com.dee.registration_login_system.service.ValidationGroups;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    @NotEmpty(groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class}, message = "First Name should not be empty")
    private String firstName;
    @NotEmpty(groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class}, message = "Last Name should not be empty")
    private String lastName;
    @NotEmpty(groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class}, message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty(groups = ValidationGroups.OnCreate.class, message = "Password should not be empty")
    private String password;
}
