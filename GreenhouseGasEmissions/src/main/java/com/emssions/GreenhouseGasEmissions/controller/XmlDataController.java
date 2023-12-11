package com.emssions.GreenhouseGasEmissions.controller;

import com.emssions.GreenhouseGasEmissions.entity.XmlData;
import com.emssions.GreenhouseGasEmissions.service.UserService;
import com.emssions.GreenhouseGasEmissions.service.XmlDataService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class XmlDataController {

	private final XmlDataService xmlDataService;
	private final UserService userService;

	public XmlDataController(XmlDataService xmlDataService, UserService userService) {
        this.xmlDataService = xmlDataService;
        this.userService = userService;
    }

	@GetMapping("/category/{category}")
    public ResponseEntity<List<XmlData>> getDataByCategory(@PathVariable String category, @RequestParam String username) {
        if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<XmlData> data = xmlDataService.findByCategory(category);
        return ResponseEntity.ok(data);
    }

	@PostMapping("/parse")
	public ResponseEntity<String> parseAndSave(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		try {
			xmlDataService.parseXmlAndSave();
			return ResponseEntity.ok("Data parsed and saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}

	@DeleteMapping("/deleteALL")
	public ResponseEntity<String> clearXmlData(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		try {
			xmlDataService.clearXmlData();
			return ResponseEntity.ok("XmlData table cleared successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
		}
	}

	@PutMapping("/data/{id}")
	public ResponseEntity<XmlData> updateDataById(@PathVariable Long id, @RequestBody XmlData newData, @RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		XmlData updatedData = xmlDataService.updateDataById(id, newData);
		return updatedData != null ? ResponseEntity.ok(updatedData) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/data/{id}")
	public ResponseEntity<Void> deleteDataById(@PathVariable Long id, @RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		xmlDataService.deleteDataById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/data/{id}")
	public ResponseEntity<XmlData> getDataById(@PathVariable Long id, @RequestParam String username) {
	    if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	    XmlData data = xmlDataService.getDataById(id);
	    return data != null
	            ? ResponseEntity.ok(data)
	            : ResponseEntity.notFound().build();
	}

	@PostMapping("/data")
	public ResponseEntity<XmlData> postData(@RequestBody XmlData newData, @RequestParam String username) {
	    if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	    XmlData savedData = xmlDataService.postData(newData);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedData);
	}

	@GetMapping("/data/all")
	public ResponseEntity<List<XmlData>> getAllData(@RequestParam String username) {
		if (!userService.isUserLoggedIn(username)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		List<XmlData> data = xmlDataService.getAllData();
		return ResponseEntity.ok(data);
	}
}