package com.emssions.GreenhouseGasEmissions.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Emissions {
	 @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    private String category;
	    private String gasUnits;
	    private Double predictedValue;
	    private Double actualValue;
	    private Double difference;
		private String description;


	    public Emissions() {
	        // Default constructor
	    }

	    public Emissions(String category, String gasUnits, Double predictedValue, Double actualValue, Double difference, String description) {
	        this.category = category;
	        this.gasUnits = gasUnits;
	        this.predictedValue = predictedValue;
	        this.actualValue = actualValue;
	        this.difference = difference;
	        this.description = description;
	    }


	    public Double getDifference() {
			return difference;
		}

		public void setDifference(Double difference) {
			this.difference = difference;
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

	    public Double getPredictedValue() {
			return predictedValue;
		}

		public void setPredictedValue(Double predictedValue) {
			this.predictedValue = predictedValue;
		}

		public Double getActualValue() {
			return actualValue;
		}

		public void setActualValue(Double actualValue) {
			this.actualValue = actualValue;
		}

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }
	}

