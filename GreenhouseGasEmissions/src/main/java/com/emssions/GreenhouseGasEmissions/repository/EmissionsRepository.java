package com.emssions.GreenhouseGasEmissions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emssions.GreenhouseGasEmissions.entity.Emissions;


public interface EmissionsRepository extends JpaRepository<Emissions, Long> {
	
	List<Emissions> findByCategory(String category);
}
