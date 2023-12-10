package com.emssions.GreenhouseGasEmissions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emssions.GreenhouseGasEmissions.entity.User;
import com.emssions.GreenhouseGasEmissions.service.UserService;

@RestController
public class HomeController {
    
	@Autowired
	 private UserService userService;
	
	//Testing spring app purposes
	@GetMapping("/home")
	public String home() {
		return "Welcome to spring boot reg proccess";
	}
	
	@PostMapping("/saveUser")
	public String saveUser(@RequestBody User user) {
	    userService.saveUser(user);
	    return "User Created successfully";
	}
	
	
	
}
