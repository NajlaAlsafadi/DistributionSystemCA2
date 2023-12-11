package com.emssions.GreenhouseGasEmissions.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.emssions.GreenhouseGasEmissions.entity.User;
import com.emssions.GreenhouseGasEmissions.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
  //Testing spring app purposes
  	@GetMapping("/home")
  	public String home() {
  		return "Welcome to spring boot reg proccess";
  	}

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
    
    @GetMapping("/getUserData")
    public String getUserData(@RequestParam String username) {
        if (userService.isUserLoggedIn(username)) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return "User data: " + user.toString();
            } else {
                return "User not found";
            }
        } else {
            return "User not logged in";
        }
    }

    @DeleteMapping("/deleteUserData")
    public String deleteUserData(@RequestParam String username) {
        if (userService.isUserLoggedIn(username)) {
            boolean deleted = userService.deleteUserByUsername(username);
            if (deleted) {
                return "User deleted successfully";
            } else {
                return "Error deleting user";
            }
        } else {
            return "User not logged in";
        }
    }

    @PutMapping("/updateUserData")
    public String updateUserData(@RequestParam String username, @RequestBody User updatedUser) {
        if (userService.isUserLoggedIn(username)) {
            boolean updated = userService.updateUserData(username, updatedUser);
            if (updated) {
                return "User updated successfully";
            } else {
                return "Error updating user";
            }
        } else {
            return "User not logged in";
        }
    }
}