package com.myapp.spring.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import  com.myapp.spring.model.Product;

import net.codejava.ProductRepository;

@SpringBootTest
public class ProductRepositoryTest {
	
	@Autowired
	private ProductRepository repository;
	
	private static File DATA_JSON= Paths.get("src","test","resources","products.json").toFile();
	
	
	@BeforeEach
	public void setUp() throws JsonParseException, JsonMappingException, IOException {
		
	Product products[]=new ObjectMapper().readValue(DATA_JSON, Product[].class);
	
	// save each product to database
	Arrays.stream(products).forEach(repository::save);	
		
		
	}
	
	@AfterEach
	public void cleanUp() {
		repository.deleteAll();
		
	}
	
	@Test
	@DisplayName("Test product not found for a non existing id")
	public void testProductNotFoundForNonExistingId() {
		
		// given three products in the database
		
		// when we retrieve a product using non existing id
		Product product=repository.findById(100).orElseGet(()-> new Product());
		
		// Then perform Assert Conditions To validate
		Assertions.assertNull(product.getBaggageId(), 
				"Product With Id 100 should not exist");
		
		}
	
	
	@Test
	@DisplayName("Test product saved sucessfully")
	public void testProductSavedSucessfully() {
		
		// given a mock product
		Product product = new Product("Rahul", "dd1234", 29, "p.rahulsai@gmail.com");
		product.setBaggageId(5);
		
		// when we retrieve a product using non existing id
		Product savedProduct=repository.save(product);
		
		// Then perform Assert Conditions To validate
		Assertions.assertNotNull(savedProduct, 
				"New Product should be saved");
		
		Assertions.assertNotNull(savedProduct.getBaggageId(), 
				"New Product should have id");
		Assertions.assertEquals(product.getFirstName(), 
				savedProduct.getFirstName());
		
		
		}
	
	@Test
	@DisplayName("Test product updated sucessfully")
	public void testProductUpdatedSucessfully() {
		
		// given a mock product
		Product product = new Product("sai", "de1234", 15, "sai@gmail.com");
		product.setBaggageId(1);
		
		// when we retrieve a product using non existing id
		Product updatedProduct=repository.save(product);
		
		
		Assertions.assertEquals(product.getSeatno(), 
				updatedProduct.getSeatno());
		
		
		}
	

}