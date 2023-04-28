package com.fam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	
	  @Override public void addViewControllers(ViewControllerRegistry registry) {
	  registry.addViewController("/access-denied").setViewName("access-denied");
	  registry.addViewController("/").setViewName("homepage.html");
	  registry.addViewController("/about-us").setViewName("about-us");
	  registry.addViewController("/parent/dashboard").setViewName("event.jsp"); }
	 
	 
}