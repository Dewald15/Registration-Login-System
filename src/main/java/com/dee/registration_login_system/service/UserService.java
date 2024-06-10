package com.dee.registration_login_system.service;

import com.dee.registration_login_system.dto.UserDto;
import com.dee.registration_login_system.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
}
