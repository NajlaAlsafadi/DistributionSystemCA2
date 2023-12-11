package com.emssions.GreenhouseGasEmissions.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.emssions.GreenhouseGasEmissions.entity.JsonData;

public interface JsonDataRepository extends JpaRepository<JsonData, Long> {
	
	List<JsonData> findByCategory(String category);
}
