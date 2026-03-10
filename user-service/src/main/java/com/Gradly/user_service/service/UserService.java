package com.Gradly.user_service.service;

import com.Gradly.user_service.dto.UserResponse;
import com.Gradly.user_service.model.User;
import com.Gradly.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<UserResponse> searchUsers(String query) {
        return userRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getName()))
                .collect(Collectors.toList());
    }
}
