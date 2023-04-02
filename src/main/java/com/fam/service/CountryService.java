package com.fam.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.fam.model.Country;

public interface CountryService {
	
	List<Country> parseXML(Long id) throws ParserConfigurationException, SAXException, IOException;
	Country saveCountry(Country country);
	List<Country> getAllCountries();
	Country getCountryById(long id);
	Country updateCountry(Long id, Country updatedCountry);
	void deleteCountry(long id);
}