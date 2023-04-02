package com.fam.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// COUNTRY ENTITY CLASS
@EnableJpaRepositories
@Entity
@Table(name = "countries")
public class Country {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name = "name")
    private String name;

    @Column(name = "yearWeek")
    private String yearWeek;

    @Column(name = "value")
    private String value;

//    Trying to Join Tables
//    @OneToMany
//    @JoinColumn(name = "country_id")
//    private List<Variant> variants;


    public Country() {
	}

	public Country(Long id, String name, String yearWeek, String value) {
		this.id = id;
		this.name = name;
		this.yearWeek = yearWeek;
		this.value = value;
	}

	// GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYearWeek() {
        return yearWeek;
    }

    public void setYearWeek(String yearWeek) {
        this.yearWeek = yearWeek;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
//  Trying to Join Tables
//	public List<Variant> getVariants() {
//	    return variants;
//	}
//
//	public void setVariants(List<Variant> variants) {
//	    this.variants = variants;
//	}

}