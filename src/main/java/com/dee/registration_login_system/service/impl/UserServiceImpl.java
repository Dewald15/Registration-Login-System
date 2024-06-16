package com.dee.registration_login_system.service.impl;

import com.dee.registration_login_system.dto.UserDto;
import com.dee.registration_login_system.entity.Role;
import com.dee.registration_login_system.entity.User;
import com.dee.registration_login_system.repository.RoleRepository;
import com.dee.registration_login_system.repository.UserRepository;
import com.dee.registration_login_system.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        String roleAdminString = "ROLE_ADMIN";
        String roleUserString = "ROLE_USER";

        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role roleAdmin = roleRepository.findByName(roleAdminString);
        Role roleUser = roleRepository.findByName(roleUserString);

        if(roleAdmin == null){
            roleAdmin = saveRole(roleAdminString);
            user.setRoles(Arrays.asList(roleAdmin));
            userRepository.save(user);
        } else if(roleUser == null){
            roleUser = saveRole(roleUserString);
            user.setRoles(Arrays.asList(roleUser));
            userRepository.save(user);
        } else if(roleUser != null){
            user.setRoles(Arrays.asList(roleUser));
            userRepository.save(user);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map((user) -> mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));

        // Remove roles associated with the user
        user.getRoles().clear();
        userRepository.save(user);

        // Delete the user
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).get();
        return mapToUserDto(user);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User existingUser = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userDto.getId()));

        existingUser.setName(userDto.getFirstName() + " " + userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());

        // Update password only if it is provided
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        userRepository.save(existingUser);
    }

    @Transactional
    @Override
    public void changeUserRole(Long userId, String roleName) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

        // Find the new role by name
        Role newRole = roleRepository.findByName(roleName);

        if (newRole == null) {
            throw new IllegalArgumentException("Invalid role name");
        }

        // Clear the current roles
        user.getRoles().clear();

        // Set the new role
        user.getRoles().add(newRole);

        // Save the updated user back to the repository
        userRepository.save(user);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        String[] str = user.getName().split(" ", 2); // Split into at most 2 parts
        userDto.setId(user.getId());
        userDto.setFirstName(str[0]);
        // Set the last name: if there is more than one part, join the remaining parts
        userDto.setLastName(str.length > 1 ? str[1] : "");
        userDto.setEmail(user.getEmail());

        // Convert the list of roles to a comma-separated string
        String roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(", "));
        userDto.setRoles(roles);
        return userDto;
    }

    private User mapToUser(UserDto userDto){
        User user = new User();
        String name = userDto.getFirstName() + " " + userDto.getLastName();
        user.setId(userDto.getId());
        user.setName(name);
        user.setEmail(userDto.getEmail());
        return user;
    }

    private Role saveRole(String newRole){
        Role role = new Role();
        role.setName(newRole);
        return roleRepository.save(role);
    }
}
