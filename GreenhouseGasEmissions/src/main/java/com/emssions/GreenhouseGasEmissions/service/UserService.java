package com.emssions.GreenhouseGasEmissions.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emssions.GreenhouseGasEmissions.entity.User;
import com.emssions.GreenhouseGasEmissions.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private Map<String, User> sessionMap = new HashMap<>();

    public void registerUser(User user) {
        if (userRepository.existsById(user.getUsername())) {
            throw new RuntimeException("Username already exists. Please choose a different username.");
        }
        userRepository.save(user);
    }

    public String loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            sessionMap.put(username, user);
            return "Login successful!";
        } else {
            return "Invalid username or password";
        }
    }

    public User getUserByUsername(String username) {
        return sessionMap.get(username);
    }

    public boolean deleteUserByUsername(String username) {
        if (sessionMap.containsKey(username)) {
            userRepository.delete(sessionMap.get(username));
            sessionMap.remove(username);
            return true;
        }
        return false;
    }

    public boolean updateUserData(String username, User updatedUser) {
        if (sessionMap.containsKey(username)) {
            User existingUser = sessionMap.get(username);
            existingUser.setPassword(updatedUser.getPassword());
            userRepository.save(existingUser);
            return true;
        }
        return false;
    }

    public boolean isUserLoggedIn(String username) {
        return sessionMap.containsKey(username);
    }
}