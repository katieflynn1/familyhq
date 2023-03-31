package com.fam.service;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.fam.model.Country;
import com.fam.repository.CountryRepository;

public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryRepository countryRepository;

	public CountryServiceImpl(CountryRepository countryRepository) {
		super();
		this.countryRepository = countryRepository;
	}

	@Override
	public Country saveCountry(Country country) {
		return countryRepository.save(country);
	}

	@Override
	public List<Country> parseXML(Long id) throws ParserConfigurationException, SAXException, IOException {

		List<Country> countries = new ArrayList<>();
		int weekNumber = 0;
		int thisYear = 2022;

		// Connect to the web page
		String url = "https://www.epochconverter.com/weeks/2022";
		org.jsoup.nodes.Document doc = Jsoup.connect(url).get();

		// Get all table row elements
		Elements allRows = doc.getElementsByTag("tr");

		LocalDate currentDate = LocalDate.of(thisYear, 1, 1);
		LocalDate tenWeeksAgo = currentDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY)).minusWeeks(10);

		// Get the week number for the date 10 weeks ago
		for (Element row : allRows) {
			// Get the cell at the specified index
			if (row.attr("class").equalsIgnoreCase("currentweek")) {
				String weekString = doc.getElementsByTag("td").get(0).text();
				Pattern pattern = Pattern.compile("\\d+");
				Matcher matcher = pattern.matcher(weekString);
				if (matcher.find()) {
					weekNumber = Integer.parseInt(matcher.group());
				}
				break;
			}
		}

		// Parse the XML data from the file
		File xmlFile = new File("coviddata.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document coviddoc = builder.parse(xmlFile);

		// Get all "Sheet" elements
		NodeList sheets = coviddoc.getElementsByTagName("fme:Sheet");

		// Iterate through the "Sheet" elements
		for (int i = 0; i < sheets.getLength(); i++) {
			Node sheet = sheets.item(i);

			if (sheet.getNodeType() == Node.ELEMENT_NODE) {
				org.w3c.dom.Element sheetElement = (org.w3c.dom.Element) sheet;

				String name = sheetElement.getElementsByTagName("fme:country").item(0).getTextContent();
				String indicator = sheetElement.getElementsByTagName("fme:indicator").item(0).getTextContent();
				String date = sheetElement.getElementsByTagName("fme:date").item(0).getTextContent();
				String yearWeek = sheetElement.getElementsByTagName("fme:year_week").item(0).getTextContent();
				String value = sheetElement.getElementsByTagName("fme:value").item(0).getTextContent();

				// Extract the week number from the year_week value
				String[] yearWeekParts = yearWeek.split("-");

				// Check if the indicator is "Daily hospital occupancy" and the year is thisYear
				if (indicator.equalsIgnoreCase("Daily hospital occupancy")
						&& yearWeekParts[0].equals(String.valueOf(thisYear))) {

					// Extract the week number from the year_week value
					String weekNumberString = yearWeekParts[1].substring(1);
					int yearWeekInt = Integer.parseInt(weekNumberString);

					if (yearWeekInt == weekNumber - 10) {
						// Check if the country is already in the list
						Country c = findCountry(countries, name);
						if (c == null) {
							c = new Country(id, name, yearWeek, value);
							countries.add(c);
						} else {
							c.setValue(String.valueOf(Double.parseDouble(c.getValue()) + Double.parseDouble(value)));
						}
					}
				}
			}
		}

		for (Country c : countries) {
			double average = Double.parseDouble(c.getValue()) / 7;
			c.setValue(String.valueOf(average));
		}
		return countries;
	}

	private static Country findCountry(List<Country> countries, String name) {
		for (Country c : countries) {
			if (c.getName().equalsIgnoreCase(name)) {
				return c;
			}
		}
		return null;
	}

	// GET ALL COUNTRIES
	@Override
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}

	// GET COUNTRY BY ID
	@Override
	public Country getCountryById(long id) {
		return countryRepository.findById(id).get();

	}

	// UPDATE COUNTRY BY ID
	@Override
	public Country updateCountry(Long id, Country country) {
		// Find the country with the given id
		Country updatedCountry = countryRepository.findById(id).orElse(null);
		if (updatedCountry != null) {
			updatedCountry.setId(updatedCountry.getId());
			updatedCountry.setName(updatedCountry.getName());
			updatedCountry.setYearWeek(updatedCountry.getYearWeek());
			updatedCountry.setValue(updatedCountry.getValue());

			countryRepository.save(updatedCountry);
		}
		return updatedCountry;
	}

	// DELETE COUNTRY BY ID
	@Override
	public void deleteCountry(long id) {
		if (countryRepository.findById(id) != null)
			;
		countryRepository.deleteById(id);
	}
}