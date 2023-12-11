package com.emssions.GreenhouseGasEmissions.service;

import com.emssions.GreenhouseGasEmissions.entity.XmlData;
import com.emssions.GreenhouseGasEmissions.repository.XmlDataRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

@Service
public class XmlDataService {

	private final XmlDataRepository xmlDataRepository;

	public XmlDataService(XmlDataRepository xmlDataRepository) {
		this.xmlDataRepository = xmlDataRepository;
	}

	public List<XmlData> findByCategory(String category) {
		return xmlDataRepository.findByCategory(category.trim());
	}

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

	public void clearXmlData() {
		xmlDataRepository.deleteAll();
	}

	public void parseXmlAndSave() throws Exception {
		File xmlFile = new ClassPathResource("GreenhouseEmissions.xml").getFile();

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();

		NodeList nodeList = doc.getElementsByTagName("Row");

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				org.w3c.dom.Element element = (org.w3c.dom.Element) node;
				String category = element.getElementsByTagName("Category__1_3").item(0).getTextContent();
				String year = element.getElementsByTagName("Year").item(0).getTextContent();
				String scenario = element.getElementsByTagName("Scenario").item(0).getTextContent();
				String gasUnits = element.getElementsByTagName("Gas___Units").item(0).getTextContent();
				String value = element.getElementsByTagName("Value").item(0).getTextContent();

				double valueStr = 0;
				if (value != null && !value.isEmpty()) {
					valueStr = Double.parseDouble(value);
				}

				if (scenario.equals("WEM") && Integer.parseInt(year) == 2023 && valueStr > 0) {
					String description = fetchDescription(category);
					XmlData xmlData = new XmlData(category, gasUnits, valueStr, description);
					xmlDataRepository.save(xmlData);
				}
			}
		}
	}

	public List<XmlData> getAllData() {
		return xmlDataRepository.findAll();
	}

	public XmlData getDataById(Long id) {
	    return xmlDataRepository.findById(id).orElse(null);
	}

	public void deleteDataById(Long id) {
		xmlDataRepository.deleteById(id);
	}

	public XmlData postData(XmlData newData) {
	    return xmlDataRepository.save(newData);
	}

	public XmlData updateDataById(Long id, XmlData newData) {
		if (xmlDataRepository.existsById(id)) {
			newData.setId(id);
			return xmlDataRepository.save(newData);
		}
		return null;
	}

}