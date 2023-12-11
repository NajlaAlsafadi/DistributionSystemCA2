package com.emssions.GreenhouseGasEmissions.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "Row")
public class XmlData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @XmlElement(name = "Category__1_3")
    private String category;

    @XmlElement(name = "Gas___Units")
    private String gasUnits;

    @XmlElement(name = "Value")
    private Double value;
    private String description;

    public XmlData() {
        // Default constructor
    }

    public XmlData(String category, String gasUnits, Double value, String description) {
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
