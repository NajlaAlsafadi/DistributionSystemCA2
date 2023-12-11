package com.emssions.GreenhouseGasEmissions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emssions.GreenhouseGasEmissions.entity.XmlData;

public interface XmlDataRepository extends JpaRepository<XmlData, Long> {
	
	List<XmlData> findByCategory(String category);
}
