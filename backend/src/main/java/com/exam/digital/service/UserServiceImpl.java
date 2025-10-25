package com.exam.digital.service;

import com.exam.digital.model.User;
import com.exam.digital.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository , PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Role check debug
        System.out.println("Logging in user with role: " + user.getRole());

        return user;
    }
    @Override
    public User updateUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(newRole.toUpperCase());
        return userRepository.save(user);
    }
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    @Transactional(readOnly = true) // Good practice for read operations
    public List<User> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        // *** Filter out users with the role "MODERATOR" (case-insensitive) ***
        return allUsers.stream()
                .filter(user -> !"MODERATOR".equalsIgnoreCase(user.getRole()))
                .collect(Collectors.toList());
    }
    @Override
    @Transactional // Important for delete operations
    public void deleteUser(Long userId) {
        // Check if the user exists before attempting deletion
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}