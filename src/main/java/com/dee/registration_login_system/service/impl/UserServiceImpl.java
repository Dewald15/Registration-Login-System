package com.dee.registration_login_system.service.impl;

import com.dee.registration_login_system.dto.UserDto;
import com.dee.registration_login_system.entity.Role;
import com.dee.registration_login_system.entity.User;
import com.dee.registration_login_system.repository.RoleRepository;
import com.dee.registration_login_system.repository.UserRepository;
import com.dee.registration_login_system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        Role role = roleRepository.findByName("ROLE_ADMIN");

        if(role == null){
            role = checkRoleExist();
        }

        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }
}
