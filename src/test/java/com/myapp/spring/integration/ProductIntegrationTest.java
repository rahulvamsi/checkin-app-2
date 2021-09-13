package com.myapp.spring.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import  com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

@SpringBootTest

@AutoConfigureMockMvc(addFilters = false)
public class ProductIntegrationTest {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
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
	@DisplayName("Test Product by Id - GET /api/v1/products/")
	public void testGetProductsById() throws Exception {
		
		
		// Perform GET Request
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}",1))
		// Validate Status should be 200 OK and JSON response received
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// Validate Response Body
		
		.andExpect(jsonPath("$.baggageId", is(1)))
		.andExpect(jsonPath("$.firstName", is("vamsi")))
		.andExpect(jsonPath("$.pnr", is("fe1234")))
		.andExpect(jsonPath("$.seatno", is(12)))
		.andExpect(jsonPath("$.email", is("vamsi@gmail.com")));
		
		
	}
	
	@Test
	@DisplayName("Test All Products /api/v1/products/")
	public void testGetAllProducts() throws Exception {
		
		
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
		// Validate Status should be 200 OK and JSON response received
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// Validate Response Body
		
		.andExpect(jsonPath("$[0].baggageId", is(1)))
		.andExpect(jsonPath("$[0].firstName", is("vamsi")))
		.andExpect(jsonPath("$[0].pnr", is("fe1234")))
		.andExpect(jsonPath("$[0].seatno", is(12)))
		.andExpect(jsonPath("$[0].email", is("vamsi@gmail.com")))
		
		.andExpect(jsonPath("$[1].baggageId", is(2)))
		.andExpect(jsonPath("$[1].firstName", is("Sai")))
		.andExpect(jsonPath("$[1].pnr", is("ffr123")))
		.andExpect(jsonPath("$[1].seatno", is(50)))
		.andExpect(jsonPath("$[1].email", is("p.rahul@gmail.com")));
		
		
		
		
	}
	
	//@Test
	@DisplayName("Test All Products By Price /api/v1/products/{price}")
	public void testGetAllProductsByPrice() throws Exception {
		
	
		// Prepare Mock Service Method
		Integer seatno =50;
		
		
		// Perform GET Request
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/findBySeatno/{seatno}",seatno))
		// Validate Status should be 200 OK and JSON response received
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// Validate Response Body
		
		.andExpect(jsonPath("$[0].baggageId", is(35)))
		.andExpect(jsonPath("$[0].firstName", is("test1")))
		.andExpect(jsonPath("$[0].pnr", is("kj12345")))
		.andExpect(jsonPath("$[0].seatno", is(21)))
		.andExpect(jsonPath("$[0].email", is("test3@gmail.com")))
		
		.andExpect(jsonPath("$[1].baggageId", is(36)))
		.andExpect(jsonPath("$[1].firstName", is("test2")))
		.andExpect(jsonPath("$[1].pnr", is("jk1234")))
		.andExpect(jsonPath("$[1].seatno", is(6)))
		.andExpect(jsonPath("$[1].email", is("tes4@gmail.com")))
		
		.andExpect(jsonPath("$[2].baggageId", is(37)))
		.andExpect(jsonPath("$[2].firstName", is("test3")))
		.andExpect(jsonPath("$[2].pnr", is("lk1234")))
		.andExpect(jsonPath("$[2].seatno", is(8)))
		.andExpect(jsonPath("$[2].email", is("tes5@gmail.com")));
		
		
	}
	
	//@Test
	@DisplayName("Test All Products By Price /api/v1/products?name=&seatno")
	public void testGetAllProductsByNameOrSeatno() throws Exception {
		
		
		
		// Prepare Mock Service Method
		Integer seatno =50;
		String firstName="rahul";
		
	
		
		
		// Perform GET Request
		
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/v1/products/findBySeatnoOrName")
				.queryParam("firstName",firstName)
				.queryParam("seatno", seatno.toString()))
		// Validate Status should be 200 OK and JSON response received
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// Validate Response Body
		
		.andExpect(jsonPath("$[0].baggageId", is(35)))
		.andExpect(jsonPath("$[0].firstName", is("test1")))
		.andExpect(jsonPath("$[0].pnr", is("kj12345")))
		.andExpect(jsonPath("$[0].seatno", is(21)))
		.andExpect(jsonPath("$[0].email", is("test3@gmail.com")))
		
		.andExpect(jsonPath("$[1].baggageId", is(36)))
		.andExpect(jsonPath("$[1].firstName", is("test2")))
		.andExpect(jsonPath("$[1].pnr", is("jk1234")))
		.andExpect(jsonPath("$[1].seatno", is(6)))
		.andExpect(jsonPath("$[1].email", is("tes4@gmail.com")));
		
		
		
	}
	
	
	
	
	//@Test
	@DisplayName("Test Add New Product")
	public void testAddNewProduct() throws Exception {
		
		// Prepare Mock Product
		Product newproduct = new Product("test5", "hj1234", 23, "test6@gmail.com");
		
		Product mockproduct = new Product("test6", "vg1234", 12, "test7@gmail.com");
		mockproduct.setBaggageId(50);
		// Prepare Mock Service Method
		
		
		
		// Perform GET Request
		
		mockMvc.perform(post("/api/v1/products")
		// Validate Status should be 200 OK and JSON response received
		
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(newproduct)))
		
		
		// Validate Response Body
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.baggageId", is(50)))
		.andExpect(jsonPath("$.firstName", is("test8")))
		.andExpect(jsonPath("$.pnr", is("cv123")))
		.andExpect(jsonPath("$.seatno", is(70)))
		.andExpect(jsonPath("$.email", is("set@gmail.com")));
		
		
	}
	
	

}
