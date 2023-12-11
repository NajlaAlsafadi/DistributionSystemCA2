package com.emssions.GreenhouseGasEmissions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JsonData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String category;
    private String gasUnits;
    private Double value;
    private String description;


    public JsonData() {
        // Default constructor
    }

    public JsonData(String category, String gasUnits, Double value, String description) {
        this.category = category;
        this.gasUnits = gasUnits;
        this.value = value;
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGasUnits() {
        return gasUnits;
    }

    public void setGasUnits(String gasUnits) {
        this.gasUnits = gasUnits;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
