package com.fam.controller;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xml.sax.SAXException;

import com.fam.model.Country;
import com.fam.service.CountryService;

import jakarta.websocket.server.PathParam;

@Controller
public class CountryController {

	private final CountryService countryService;

	@Autowired
	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	// PARSE COUNTRIES REST API
	@PostMapping("/parseCountries")
	public ResponseEntity<Void> saveParsedCountries() throws ParserConfigurationException, SAXException, IOException {
		List<Country> countries = countryService.parseXML(null);
		for (Country country : countries) {
			countryService.saveCountry(country);
		}
		return ResponseEntity.ok().build();
	}

	// GET ALL COUNTRIES REST API
	@GetMapping("/getCountries")
	public ResponseEntity<List<Country>> getAllCountries() {
		List<Country> countries = countryService.getAllCountries();
		return ResponseEntity.ok(countries);
	}

	// GET COUNTRY BY ID REST API
	@GetMapping("/countries/{id}")
	public ResponseEntity<Country> getCountryById(@PathParam("id") long id) {
		Country country = countryService.getCountryById(id);
		return ResponseEntity.ok(country);
	}

	// UPDATE COUNTRY BY ID
	@PutMapping("/countries/{id}")
	public ResponseEntity<Country> updateCountry(@PathVariable("id") long id, @RequestBody Country country) {
		return new ResponseEntity<Country>(countryService.updateCountry(id, country), HttpStatus.OK);
	}

	// DELETE COUNTRY REST API
	@DeleteMapping("/countries/{id}")
	public ResponseEntity<String> deleteCountry(@PathVariable("id") long id) {
		// delete employee from DB
		countryService.deleteCountry(id);

		return new ResponseEntity<String>("Country deleted successfully!.", HttpStatus.OK);
	}
}
