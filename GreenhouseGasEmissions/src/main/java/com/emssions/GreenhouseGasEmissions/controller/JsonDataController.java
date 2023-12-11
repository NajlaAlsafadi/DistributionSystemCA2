package com.emssions.GreenhouseGasEmissions.controller;

import com.emssions.GreenhouseGasEmissions.entity.JsonData;
import com.emssions.GreenhouseGasEmissions.entity.XmlData;
import com.emssions.GreenhouseGasEmissions.service.JsonDataService;
import com.emssions.GreenhouseGasEmissions.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apiJson")
public class JsonDataController {

	private final JsonDataService jsonDataService;
	private final UserService userService;

	public JsonDataController(JsonDataService jsonDataService, UserService userService) {
        this.jsonDataService = jsonDataService;
        this.userService = userService;
    }

	@GetMapping("/category/{category}")
    public ResponseEntity<List<JsonData>> getDataByCategory(@PathVariable String category, @RequestParam String username) {
        if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<JsonData> data = jsonDataService.findByCategory(category);
        return ResponseEntity.ok(data);
    }

	@PostMapping("/parse")
	public ResponseEntity<String> parseAndSave(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		try {
			jsonDataService.parseJsonAndSave();
			return ResponseEntity.ok("Data parsed and saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}

	@DeleteMapping("/deleteALL")
	public ResponseEntity<String> clearJsonData(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		try {
			jsonDataService.clearJsonData();
			return ResponseEntity.ok("JsonData table cleared successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}

	@PutMapping("/data/{id}")
	public ResponseEntity<JsonData> updateDataById(@PathVariable Long id, @RequestBody JsonData newData, @RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		JsonData updatedData = jsonDataService.updateDataById(id, newData);
		return updatedData != null ? ResponseEntity.ok(updatedData) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/data/{id}")
	public ResponseEntity<Void> deleteDataById(@PathVariable Long id, @RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		jsonDataService.deleteDataById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/data/{id}")
	public ResponseEntity<JsonData> getDataById(@PathVariable Long id, @RequestParam String username) {
	    if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	    JsonData data = jsonDataService.getDataById(id);
	    return data != null
	            ? ResponseEntity.ok(data)
	            : ResponseEntity.notFound().build();
	}

	@PostMapping("/data")
	public ResponseEntity<JsonData> postData(@RequestBody JsonData newData, @RequestParam String username) {
	    if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	    JsonData savedData = jsonDataService.postData(newData);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
	}

	@GetMapping("/data/all")
	public ResponseEntity<List<JsonData>> getAllData(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		List<JsonData> data = jsonDataService.getAllData();
		return ResponseEntity.ok(data);
		}
}