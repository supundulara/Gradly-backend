package com.Gradly.user_service.controllers;

import com.Gradly.user_service.dto.UserResponse;
import com.Gradly.user_service.model.User;
import com.Gradly.user_service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @GetMapping("/search")
    public List<UserResponse> searchUsers(@RequestParam String query) {
        return userService.searchUsers(query);
    }
}
