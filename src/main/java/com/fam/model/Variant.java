package com.fam.model;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// VARIANT ENTITY CLASS
@EnableJpaRepositories
@Entity
@Table(name = "variant")
public class Variant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code")
	private String code;

	@Column(name = "variant")
	private String variant;

	@Column(name = "numberDetectionsVariant")
	private String numberDetectionsVariant;

//    Trying to Join Tables
//    @ManyToOne
//    @JoinColumn(name = "country_id")
//    private Country country;

	public Variant(Long id, String code, String variant, String numberDetectionsVariant) {
		this.id = id;
		this.code = code;
		this.variant = variant;
		this.numberDetectionsVariant = numberDetectionsVariant;
	}

	public Variant() {

	}
	// GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	public String getNumberDetectionsVariant() {
		return numberDetectionsVariant;
	}

	public void setNumberDetectionsVariant(String numberDetectionsVariant) {
		this.numberDetectionsVariant = numberDetectionsVariant;
	}

//	public Country getCountry() {
//	    return country;
//	}
//
//	public void setCountry(Country country) {
//	    this.country = country;
//	}

}
