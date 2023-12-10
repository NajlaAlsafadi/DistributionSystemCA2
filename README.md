•	You are tasked with creating a web application to display Greenhouse Gas Emissions data for Ireland. You have been given data in multiple formats – a set of predicted emissions in XML from the Government data website https://data.gov.ie/dataset/greenhouse-gas-emissions-projections and a set of readings for 2023 in JSON format (note that these are for academic purposes only and are not accurate). Descriptions for all emission categories are available at  https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php 

•	You must parse the data and store it in your MySQL database using Hibernate. You must then make that data available to users (who must first register and login) so they can view all emissions and view by category

•	Include functionality to allow CRUD operations on emissions (create new, view, edit and delete) and users


You must parse with the following rules:

•	Value must be present and greater than 0

•	Scenario must be WEM (With Existing Measures)

•	For this year only i.e., 2023

•	You should have a total of 245 entries

•	Using the https://www.ipcc-nggip.iges.or.jp/EFDB/find_ef.php website, include a description of the category in your database

•	Make CRUD operations available for both users and emission records.

You can use Quarkus, Spring (Spring MVC, Springboot) or standalone JAX-RS Library with Hibernate
