package com.emssions.GreenhouseGasEmissions.service;

import com.emssions.GreenhouseGasEmissions.entity.JsonData;
import com.emssions.GreenhouseGasEmissions.entity.XmlData;
import com.emssions.GreenhouseGasEmissions.repository.JsonDataRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JsonDataService {

    @Autowired
    private JsonDataRepository jsonDataRepository;

    public String fetchDescription(String category) throws IOException {
        Document document = Jsoup.connect("https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php").get();
        String elements = document.select("script[type=text/javascript]").html();
        Pattern p = Pattern.compile("ipccTree.add\\(\\d+,\\s*\\d+,\\s*'([^']+)'");
        Matcher m = p.matcher(elements);

        while (m.find()) {
            String description = m.group(1);
            String number = description.split(" ")[0];
            if (category.equals(number + ".") || category.equals(number)) {
            	System.out.println("Found description: " + description);
                return description;
            }
        }

        return null;
    }
    
	public List<JsonData> findByCategory(String category) {
		return jsonDataRepository.findByCategory(category.trim());
	} 
    public void clearJsonData() {
		jsonDataRepository.deleteAll();
	}
    public void parseJsonAndSave() {
        try {
            File file = new ClassPathResource("GreenhouseGasEmissions2023.json").getFile();
            byte[] jsonData = Files.readAllBytes(file.toPath());
            ObjectMapper objectMapper = new ObjectMapper();
            
            JsonNode rootNode = objectMapper.readTree(jsonData);
            JsonNode emissionsArray = rootNode.path("Emissions");

            if (emissionsArray.isArray()) {
                for (JsonNode emissionsNode : emissionsArray) {
                    String category = emissionsNode.path("Category").asText();
                    String gasUnits = emissionsNode.path("Gas Units").asText();
                    Double value = emissionsNode.path("Value").asDouble();
                    String description = fetchDescription(category);

                    JsonData jsonDataObject = new JsonData(category, gasUnits, value, description);
                    jsonDataRepository.save(jsonDataObject);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<JsonData> getAllData() {
		return jsonDataRepository.findAll();
	}

	public JsonData getDataById(Long id) {
	    return jsonDataRepository.findById(id).orElse(null);
	}

	public void deleteDataById(Long id) {
		jsonDataRepository.deleteById(id);
	}

	public JsonData postData(JsonData newData) {
	    return jsonDataRepository.save(newData);
	}

	public JsonData updateDataById(Long id, JsonData newData) {
		if (jsonDataRepository.existsById(id)) {
			newData.setId(id);
			return jsonDataRepository.save(newData);
		}
		return null;
	}
}