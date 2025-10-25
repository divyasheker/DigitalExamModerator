
package com.exam.digital.service;

import com.exam.digital.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> getUserByEmail(String email);
    List<User> getAllUsers();
    User login(String email, String password);
    User updateUserRole(Long userId, String newRole);
    void deleteUser(Long userId);

}
