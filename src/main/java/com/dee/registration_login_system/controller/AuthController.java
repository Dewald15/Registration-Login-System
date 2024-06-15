package com.dee.registration_login_system.controller;

import com.dee.registration_login_system.dto.UserDto;
import com.dee.registration_login_system.entity.Role;
import com.dee.registration_login_system.entity.User;
import com.dee.registration_login_system.security.CustomUserDetails;
import com.dee.registration_login_system.service.UserService;
import com.dee.registration_login_system.service.ValidationGroups;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class AuthController {

    UserService userService;

    @GetMapping("/index")
    public String home(Model model, Principal principal){
        model.addAttribute("authenticated", principal != null);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal){
        model.addAttribute("authenticated", principal != null);
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model, Principal principal){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        model.addAttribute("authenticated", principal != null);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Validated(ValidationGroups.OnCreate.class) @Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "Email already exists!");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model, Principal principal){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("authenticated", principal != null);
        model.addAttribute("users", users);
        if (principal != null) {
            CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
            Long userId = userDetails.getUserId();
            model.addAttribute("userId", userId);
        }
        // Fetch all roles and add to model
        List<Role> roles = userService.findAllRoles();
        model.addAttribute("roles", roles);
        return "users";
    }

    @GetMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId, Principal principal) {
        // Get the logged-in user's ID
        CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
        Long currentUserId = userDetails.getUserId();

        // Check if the logged-in user is trying to delete their own account
        if (userId.equals(currentUserId)) {
            // Redirect to an error page or show a message
            return "redirect:/error?message=Cannot delete your own account";
        }

        // Proceed with deletion if it's not the current user
        userService.deleteUser(userId);
        return "redirect:/users";
    }

    @GetMapping("/user/{userId}/edit")
    public String editUser(@PathVariable("userId") Long userId,
                           Model model,
                           Principal principal){
        // Get the logged-in user's ID
        CustomUserDetails userDetails = (CustomUserDetails) ((Authentication) principal).getPrincipal();
        Long currentUserId = userDetails.getUserId();

        // Check if the logged-in user is trying to delete their own account
        if (!userId.equals(currentUserId)) {
            // Redirect to an error page or show a message
            return "redirect:/error?message=Cannot edit other user accounts";
        }
        UserDto user = userService.getUserById(userId);
        model.addAttribute("user", user);
        model.addAttribute("authenticated", principal != null);
        return "edit_user";
    }

    @PostMapping("/user/{userId}")
    public String updateUser(@PathVariable("userId") Long userId,
                             @Validated(ValidationGroups.OnUpdate.class) @Valid @ModelAttribute("user") UserDto userDto,
                             BindingResult result,
                             Model model,
                             Principal principal){
        if(result.hasErrors()){
            userDto.setId(userId);
            model.addAttribute("authenticated", principal != null);
            model.addAttribute("user", userDto);
            return "edit_user";
        }
        userDto.setId(userId);
        userService.updateUser(userDto);
        return "redirect:/users";
    }

    @GetMapping("/user/{userId}/view")
    public String viewUser(@PathVariable("userId") Long userId,
                           Model model,
                           Principal principal){
        UserDto userDto = userService.getUserById(userId);
        model.addAttribute("authenticated", principal != null);
        model.addAttribute("user", userDto);
        return "view_user";
    }

    @PostMapping("/user/{userId}/changeRole")
    @ResponseBody
    public ResponseEntity<String> changeUserRole(@PathVariable("userId") Long userId,
                                                 @RequestBody Map<String, String> request) {
        String roleName = request.get("roleName");
        try {
            userService.changeUserRole(userId, roleName);
            return ResponseEntity.ok("User role updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user role");
        }
    }
}
