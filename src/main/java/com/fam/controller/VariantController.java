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
import com.fam.model.Variant;
import com.fam.service.VariantService;

import jakarta.websocket.server.PathParam;

@Controller
public class VariantController {

	private final VariantService variantService;

	@Autowired
	public VariantController(VariantService variantService) {
		this.variantService = variantService;
	}

	// PARSE VARIANTS REST API
	@PostMapping("/parseVariants")
	public ResponseEntity<Void> saveParsedVariants() throws ParserConfigurationException, SAXException, IOException {
		List<Variant> variants = variantService.parseCovidVariant(null);
		for (Variant variant : variants) {
			variantService.saveVariant(variant);
		}
		return ResponseEntity.ok().build();
	}

	// GET ALL VARIANTS REST API
	@GetMapping("/getVariants")
	public ResponseEntity<List<Variant>> getAllVariants() {
		List<Variant> variants = variantService.getAllVariants();
		return ResponseEntity.ok(variants);
	}

	// GET VARIANTS BY ID REST API
	@GetMapping("/variants/{id}")
	public ResponseEntity<Variant> getVariantById(@PathParam("id") long id) {
		Variant variant = variantService.getVariantById(id);
		return ResponseEntity.ok(variant);
	}

	// UPDATE VARIANT BY ID
	@PutMapping("/variants/{id}")
	public Variant updateVariant(@PathVariable Long id, @RequestBody Variant updatedVariant) {
		return variantService.updateVariant(id, updatedVariant);
	}

	// DELETE VARIANTS REST API
	@DeleteMapping("/variants/{id}")
	public ResponseEntity<String> deleteVariant(@PathVariable("id") long id) {
		variantService.deleteVariant(id);

		return new ResponseEntity<String>("Variant deleted successfully!.", HttpStatus.OK);
	}
}