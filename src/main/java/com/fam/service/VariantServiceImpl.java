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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import com.fam.model.Country;
import com.fam.model.Variant;
import com.fam.repository.CountryRepository;
import com.fam.repository.VariantRepository;

public class VariantServiceImpl implements VariantService {

	@Autowired
	VariantRepository variantRepository;

	public VariantServiceImpl(VariantRepository variantRepository) {
		super();
		this.variantRepository = variantRepository;
	}

	@Override
	public Variant saveVariant(Variant variant) {
		return variantRepository.save(variant);
	}

	public List<Variant> parseCovidVariant(Long id) throws IOException, ParserConfigurationException, SAXException {
	    
		List<Variant> variants = new ArrayList<>();

	    // Parse the XML data from the file
	    File xmlFile = new File("covidvariant.xml");
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    org.w3c.dom.Document covidVariantsDoc = builder.parse(xmlFile);
	    
	    // Get all "Sheet" elements
	    NodeList sheets = covidVariantsDoc.getElementsByTagName("fme:Sheet");
	    // Iterate through the "Sheet" elements
	    for (int i = 0; i < sheets.getLength(); i++) {
	        Node sheet = sheets.item(i);
	        if (sheet.getNodeType() == Node.ELEMENT_NODE) {
	            org.w3c.dom.Element sheetElement = (org.w3c.dom.Element) sheet;
	            
	            String code = sheetElement.getElementsByTagName("fme:country_code").item(0).getTextContent();
	            String yearWeek = sheetElement.getElementsByTagName("fme:year_week").item(0).getTextContent();
	            String variant = sheetElement.getElementsByTagName("fme:variant").item(0).getTextContent();
	            String numberDetectionsVariant = sheetElement.getElementsByTagName("fme:number_detections_variant")
	                .item(0).getTextContent();
	            
	            if (numberDetectionsVariant != null && !numberDetectionsVariant.isEmpty()) {
	                int numberDetectionsVariantInt = Integer.parseInt(numberDetectionsVariant);
	                if (numberDetectionsVariantInt > 0) {
	                    Variant v = new Variant(id, code, variant, numberDetectionsVariant);
	                    v.setCode(code);
	                    v.setVariant(variant);
	                    v.setNumberDetectionsVariant(numberDetectionsVariant);
	                    variants.add(v);
	                }
	        }
	    }
	}
		return variants;
}
	        // GET ALL VARIANTS
			@Override
			public List<Variant> getAllVariants() {
				return variantRepository.findAll();
			}
			
			// GET COUNTRY BY ID
			@Override
			public Variant getVariantById(long id) {
				return variantRepository.findById(id).get();

			}
			
			// UPDATE COUNTRY BY ID
			@Override
			public Variant updateVariant(Long id, Variant updatedVariant) {
			    // Find the country with the given id
				Variant variant = variantRepository.findById(id).orElse(null);
			    if (variant != null) {
			    	variant.setId(updatedVariant.getId());
			    	variant.setCode(updatedVariant.getCode());
			    	variant.setVariant(updatedVariant.getVariant());
			    	variant.setNumberDetectionsVariant(updatedVariant.getNumberDetectionsVariant());
			    }
			    return variantRepository.save(variant);
			}
			
			// DELETE COUNTRY BY ID
			@Override
			public void deleteVariant(long id) {
				if( variantRepository.findById(id) != null);
				variantRepository.deleteById(id);
			}
}

	