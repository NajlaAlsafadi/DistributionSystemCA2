package com.emssions.GreenhouseGasEmissions.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
    
    @GetMapping("/getUserData")
    public User getUserData(@RequestParam String username) {
        if (userService.isUserLoggedIn(username)) {
            User user = userService.getUserByUsername(username);
            if (user != null) {
                return user;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
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