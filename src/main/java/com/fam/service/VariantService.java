package com.fam.service;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.fam.model.Variant;

public interface VariantService {

	List<Variant> parseCovidVariant(Long id) throws IOException, ParserConfigurationException, SAXException;
	Variant saveVariant(Variant variant);
	List<Variant> getAllVariants();
	Variant getVariantById(long id);
	Variant updateVariant(Long id, Variant updatedVariant);
	void deleteVariant(long id);
}
