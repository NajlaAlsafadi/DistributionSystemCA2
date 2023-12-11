package com.emssions.GreenhouseGasEmissions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emssions.GreenhouseGasEmissions.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	User findByUsername(String username);

}
