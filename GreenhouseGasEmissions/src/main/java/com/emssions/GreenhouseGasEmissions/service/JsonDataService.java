package com.emssions.GreenhouseGasEmissions.service;

import org.springframework.stereotype.Service;

import com.emssions.GreenhouseGasEmissions.entity.JsonData;
import com.emssions.GreenhouseGasEmissions.repository.JsonDataRepository;

@Service
public class JsonDataService {
    
    private final JsonDataRepository jsonDataRepository;

    public JsonDataService(JsonDataRepository jsonDataRepository) {
        this.jsonDataRepository = jsonDataRepository;
    }

    public JsonData save(JsonData jsonData) {
        return jsonDataRepository.save(jsonData);
    }
}
