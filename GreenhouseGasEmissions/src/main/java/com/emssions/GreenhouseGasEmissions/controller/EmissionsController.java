package com.emssions.GreenhouseGasEmissions.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emssions.GreenhouseGasEmissions.entity.Emissions;

import com.emssions.GreenhouseGasEmissions.service.EmissionsService;
import com.emssions.GreenhouseGasEmissions.service.UserService;

@RestController
@RequestMapping("/apiEmissions")
public class EmissionsController {
	
	
	private final EmissionsService emissionsService;
	private final UserService userService;
	
	
	public EmissionsController(EmissionsService emissionsService, UserService userService) {
        this.emissionsService = emissionsService;
        this.userService = userService;
    }
	

	@GetMapping("/data/all")
	public ResponseEntity<List<Emissions>> getAllData(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		List<Emissions> data = emissionsService.getAllData();
		return ResponseEntity.ok(data);
	}
		
	@GetMapping("/data/{id}")
	public ResponseEntity<Emissions> getDataById(@PathVariable Long id, @RequestParam String username) {
	    if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	    Emissions data = emissionsService.getDataById(id);
	    return data != null
	            ? ResponseEntity.ok(data)
	            : ResponseEntity.notFound().build();
	}
	@GetMapping("/category/{category}")
    public ResponseEntity<List<Emissions>> getDataByCategory(@PathVariable String category, @RequestParam String username) {
        if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<Emissions> data = emissionsService.findByCategory(category);
        return ResponseEntity.ok(data);
    }
	
	@PostMapping("/parseFile")
	public ResponseEntity<String> parseAndSave(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		try {
			emissionsService.parseAndSave();
			return ResponseEntity.ok("Data parsed and saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}
	
	@PostMapping("/data")
	public ResponseEntity<Emissions> postData(@RequestBody Emissions newData, @RequestParam String username) {
	    if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	    Emissions savedData = emissionsService.postData(newData);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
	}
		@PutMapping("/data/{id}")
	public ResponseEntity<Emissions> updateDataById(@PathVariable Long id, @RequestBody Emissions newData, @RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		Emissions updatedData = emissionsService.updateDataById(id, newData);
		return updatedData != null ? ResponseEntity.ok(updatedData) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/deleteALL")
	public ResponseEntity<String> clearEmissions(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		try {
			emissionsService.clearEmissions();
			return ResponseEntity.ok("Emissions table cleared successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}

	@DeleteMapping("/data/{id}")
	public ResponseEntity<Void> deleteDataById(@PathVariable Long id, @RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		emissionsService.deleteDataById(id);
		return ResponseEntity.noContent().build();
	}




}

