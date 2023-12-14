package com.emssions.GreenhouseGasEmissions.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.emssions.GreenhouseGasEmissions.entity.Emissions;

import com.emssions.GreenhouseGasEmissions.repository.EmissionsRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service 
public class EmissionsService {
	@Autowired
    private EmissionsRepository emissionsRepository;
	
	
	//method for scrapping the descriptions
	public String fetchDescription(String category) throws IOException {
		Document document = Jsoup.connect("https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php").get();
		String elements = document.select("script[type=text/javascript]").html();
		Pattern p = Pattern.compile("ipccTree.add\\(\\d+,\\s*\\d+,\\s*'([^']+)'");
		Matcher m = p.matcher(elements);
		String description = null;
		while (m.find()) {
			description = m.group(1);
			String number = description.split(" ")[0];
			if (category.equals(number + ".") || category.equals(number)) {
	            String[] parts = description.split(" - ", 2);
	            return (parts.length > 1) ? parts[1].trim() : null;
	        }
		}
		return null;
	}

	public List<Emissions> findByCategory(String category) {
		return emissionsRepository.findByCategory(category.trim());
	}
	public void parseAndSave() throws Exception {
	    File jsonFile = new ClassPathResource("GreenhouseGasEmissions2023.json").getFile();
	    File xmlFile = new ClassPathResource("GreenhouseEmissions.xml").getFile();
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
	    doc.getDocumentElement().normalize();
	    NodeList nodeList = doc.getElementsByTagName("Row");
	    Map<String, Emissions> validEntries = new LinkedHashMap<>();

	    for (int i = 0; i < nodeList.getLength(); i++) {
		    Node node = nodeList.item(i);
		    if (node.getNodeType() == Node.ELEMENT_NODE) {
		        org.w3c.dom.Element element = (org.w3c.dom.Element) node;
		        String category = element.getElementsByTagName("Category__1_3").item(0).getTextContent();
		        String gasUnits = element.getElementsByTagName("Gas___Units").item(0).getTextContent();
		        String valueStr = element.getElementsByTagName("Value").item(0).getTextContent();
		        Double value = 0.0;
		        if (valueStr != null && !valueStr.isEmpty()) {
		            value = Double.parseDouble(valueStr);
		        }
		        String year = element.getElementsByTagName("Year").item(0).getTextContent();
		        String scenario = element.getElementsByTagName("Scenario").item(0).getTextContent();
		        
		        if (scenario.equals("WEM") && Integer.parseInt(year) == 2023 && value > 0) {
		            Emissions emissions = new Emissions(category, gasUnits, value, null, null, null);
		            validEntries.put(category + gasUnits, emissions);
		        }
		    }
		}
	    for (Emissions emissions : validEntries.values()) {
            String description = fetchDescription(emissions.getCategory());
            emissions.setDescription(description);
        }

	    byte[] jsonData = Files.readAllBytes(jsonFile.toPath());
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode rootNode = objectMapper.readTree(jsonData);
	    JsonNode emissionsArray = rootNode.path("Emissions");

	    if (emissionsArray.isArray()) {
            for (JsonNode emissionsNode : emissionsArray) {
                String category = emissionsNode.path("Category").asText();
                String gasUnits = emissionsNode.path("Gas Units").asText();
                Double value = emissionsNode.path("Value").asDouble();

                Emissions emissions = validEntries.get(category + gasUnits);
                if (emissions != null) {
                    emissions.setActualValue(value);
                    Double difference = (emissions.getActualValue() - emissions.getPredictedValue() );
                    emissions.setDifference(difference);
                } else {
                  
                    String description = fetchDescription(category);
                    Emissions unmatchedEmissions = new Emissions(category, gasUnits, 0.0, value, value, description);
                    validEntries.put(category + gasUnits, unmatchedEmissions);
                }
            }
        }
	    for (Emissions emissions : validEntries.values()) {
	        emissionsRepository.save(emissions); 
	    }
	}
	

	public void clearEmissions() {
		emissionsRepository.deleteAll();
	}
	public List<Emissions> getAllData() {
		return emissionsRepository.findAll();
	}

	public Emissions getDataById(Long id) {
	    return emissionsRepository.findById(id).orElse(null);
	}

	public void deleteDataById(Long id) {
		emissionsRepository.deleteById(id);
	}

	public Emissions postData(Emissions newData) {
	    //re Calculate the difference 
	    Double difference = newData.getActualValue() - newData.getPredictedValue();
	    newData.setDifference(difference);
	    return emissionsRepository.save(newData);
	}

	public Emissions updateDataById(Long id, Emissions newData) {
	    if (emissionsRepository.existsById(id)) {
	        Emissions existingData = emissionsRepository.findById(id).orElse(null);
	        if (existingData != null) {
	            if (!existingData.getPredictedValue().equals(newData.getPredictedValue()) ||
	                !existingData.getActualValue().equals(newData.getActualValue())) {
	            	//re Calculate the difference 
	                Double difference = newData.getActualValue() - newData.getPredictedValue();
	                newData.setDifference(difference);
	            }
	        }
	        newData.setId(id);
	        return emissionsRepository.save(newData);
	    }
	    return null;
	}
}


