package com.emssions.GreenhouseGasEmissions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emssions.GreenhouseGasEmissions.entity.User;
import com.emssions.GreenhouseGasEmissions.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		User user1 = userRepository.save(user);
		return user1;
		
	}
	
}
