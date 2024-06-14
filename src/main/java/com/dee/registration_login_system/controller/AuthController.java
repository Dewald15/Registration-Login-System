package com.dee.registration_login_system.controller;

import com.dee.registration_login_system.dto.UserDto;
import com.dee.registration_login_system.entity.User;
import com.dee.registration_login_system.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

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
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
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
        return "users";
    }

    @GetMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return "redirect:/users";
    }
}
