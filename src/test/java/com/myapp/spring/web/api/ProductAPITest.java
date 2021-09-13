package com.myapp.spring.web.api;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.spring.model.Product;
import com.myapp.spring.repository.ProductRepository;

@SpringBootTest

@AutoConfigureMockMvc(addFilters = false)
public class ProductAPITest {
	
	@MockBean
	private ProductRepository repository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Test Product by Id - GET /api/v1/products/")
	public void testGetProductsById() throws Exception {
		
		// Prepare Mock Product
		Product product = new Product("Oneplus", "OnePlus9Pro", 70, "test9@gmail.com");
		product.setBaggageId(1);
		
		// Prepare Mock Service Method
		
		doReturn(Optional.of(product)).when(repository).findById(product.getBaggageId());
		
		// Perform GET Request
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}",1))
		// Validate Status should be 200 OK and JSON response received
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// Validate Response Body
		
		.andExpect(jsonPath("$.baggageId", is(1)))
		.andExpect(jsonPath("$.firstName", is("Oneplus")))
		.andExpect(jsonPath("$.pnr", is("OnePlus9Pro")))
		.andExpect(jsonPath("$.seatno", is(70)))
		.andExpect(jsonPath("$.email", is("test9@gmail.com")));
		
		
	}
	
	@Test
	@DisplayName("Test All Products /api/v1/products/")
	public void testGetAllProducts() throws Exception {
		
		// Prepare Mock Product
		Product product1 = new Product("vamsi", "fe1234", 12, "vamsi@gmail.com");
		product1.setBaggageId(35);
		
		Product product2 = new Product("sai", "ffr123", 50, "p.rahul@gmail.com");
		product2.setBaggageId(36);
		
		List<Product> products = new ArrayList<>();
		products.add(product1);
		products.add(product2);
		
		// Prepare Mock Service Method
		
		doReturn(products).when(repository).findAll();
		
		// Perform GET Request
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products"))
		// Validate Status should be 200 OK and JSON response received
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		
		// Validate Response Body
		
		.andExpect(jsonPath("$[0].baggageId", is(35)))
		.andExpect(jsonPath("$[0].firstName", is("vamsi")))
		.andExpect(jsonPath("$[0].pnr", is("fe1234")))
		.andExpect(jsonPath("$[0].seatno", is(12)))
		.andExpect(jsonPath("$[0].email", is("vamsi@gmail.com")))
		
		.andExpect(jsonPath("$[1].baggageId", is(36)))
		.andExpect(jsonPath("$[1].firstName", is("sai")))
		.andExpect(jsonPath("$[1].pnr", is("ffr123")))
		.andExpect(jsonPath("$[1].seatno", is(50)))
		.andExpect(jsonPath("$[1].email", is("p.rahul@gmail.com")));
		
		
		
		
		
	}
	
	/*
	 * @Test
	 * 
	 * @DisplayName("Test All Products By Price /api/v1/products/{seatno}") public
	 * void testGetAllProductsByPrice() throws Exception {
	 * 
	 * // Prepare Mock Product Product product1 = new Product("rhs", "OnePlus9Pro",
	 * 70, "test11@gmail.com"); product1.setBaggageId(35);
	 * 
	 * Product product2 = new Product("lhs", "OnePlus8Pro", 60, "test12@gmail.com");
	 * product2.setBaggageId(36);
	 * 
	 * Product product3 = new Product("rlhs", "Iphone12", 80, "test13@gmail.com");
	 * product3.setBaggageId(37);
	 * 
	 * List<Product> products = new ArrayList<>(); products.add(product1);
	 * products.add(product2); products.add(product3);
	 * 
	 * // Prepare Mock Service Method Integer seatno =50;
	 * 
	 * doReturn(Optional.of(products)).when(repository)
	 * .findBySeatnoGreaterThanEqual(seatno);
	 * 
	 * // Perform GET Request
	 * 
	 * mockMvc.perform(MockMvcRequestBuilders.get(
	 * "/api/v1/products/findBySeatno/{seatno}",seatno)) // Validate Status should
	 * be 200 OK and JSON response received .andExpect(status().isOk())
	 * .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	 * 
	 * // Validate Response Body
	 * 
	 * .andExpect(jsonPath("$[0].baggageId", is(35)))
	 * .andExpect(jsonPath("$[0].firstName", is("rhs")))
	 * .andExpect(jsonPath("$[0].pnr", is("OnePlus9Pro")))
	 * .andExpect(jsonPath("$[0].seatno", is(70))) .andExpect(jsonPath("$[0].email",
	 * is("test11@gmail.com")))
	 * 
	 * .andExpect(jsonPath("$[1].baggageId", is(36)))
	 * .andExpect(jsonPath("$[1].firstName", is("lhs")))
	 * .andExpect(jsonPath("$[1].pnr", is("OnePlus8Pro")))
	 * .andExpect(jsonPath("$[1].seatno", is(60))) .andExpect(jsonPath("$[1].email",
	 * is("tes12@gmail.com")))
	 * 
	 * .andExpect(jsonPath("$[2].baggageId", is(37)))
	 * .andExpect(jsonPath("$[2].firstName", is("rlhs")))
	 * .andExpect(jsonPath("$[2].pnr", is("Iphone12")))
	 * .andExpect(jsonPath("$[2].seatno", is(80))) .andExpect(jsonPath("$[2].email",
	 * is("tes13@gmail.com")));
	 * 
	 * 
	 * }
	 */
	
	/*
	 * @Test
	 * 
	 * @DisplayName("Test All Products By Price /api/v1/products?name=&seatno")
	 * public void testGetAllProductsByNameOrSeatno() throws Exception {
	 * 
	 * // Prepare Mock Product Product product1 = new Product("Oneplus",
	 * "OnePlus9Pro", 70, "test13@gmail.com"); product1.setBaggageId(35);
	 * 
	 * Product product2 = new Product("One", "fg1323", 60, "tes12@gmail.com");
	 * product2.setBaggageId(36);
	 * 
	 * 
	 * 
	 * List<Product> products = new ArrayList<>(); products.add(product1);
	 * products.add(product2);
	 * 
	 * 
	 * // Prepare Mock Service Method Integer seatno =50; String firstName="test8";
	 * 
	 * doReturn(Optional.of(products)).when(repository)
	 * .findByFirstNameOrSeatno(firstName, seatno);
	 * 
	 * 
	 * // Perform GET Request
	 * 
	 * 
	 * mockMvc.perform(MockMvcRequestBuilders
	 * .get("/api/v1/products/findBySeatnoOrName")
	 * .queryParam("firstName",firstName) .queryParam("seatno", seatno.toString()))
	 * // Validate Status should be 200 OK and JSON response received
	 * .andExpect(status().isOk())
	 * .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
	 * 
	 * // Validate Response Body
	 * 
	 * .andExpect(jsonPath("$[0].baggageId", is(35)))
	 * .andExpect(jsonPath("$[0].firstName", is("test1")))
	 * .andExpect(jsonPath("$[0].pnr", is("kj12345")))
	 * .andExpect(jsonPath("$[0].seatno", is(21))) .andExpect(jsonPath("$[0].email",
	 * is("test3@gmail.com")))
	 * 
	 * .andExpect(jsonPath("$[1].baggageId", is(36)))
	 * .andExpect(jsonPath("$[1].firstName", is("test2")))
	 * .andExpect(jsonPath("$[1].pnr", is("jk1234")))
	 * .andExpect(jsonPath("$[1].seatno", is(6))) .andExpect(jsonPath("$[1].email",
	 * is("tes4@gmail.com")));
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	
	@Test
	@DisplayName("Test Add New Product")
	public void testAddNewProduct() throws Exception {
		
		// Prepare Mock Product
		Product newproduct = new Product("hghsi", "hg132", 70, "tey@gmail.com");
		
		Product mockproduct = new Product("test8", "cv123", 70, "set@gmail.com");
		mockproduct.setBaggageId(50);
		// Prepare Mock Service Method
		
		doReturn(mockproduct).when(repository).save(ArgumentMatchers.any());
		
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
	@Test
	@DisplayName("Test Update Existing Product")
	public void testUpdateExistingProduct() throws Exception {
		
		// Prepare Mock Product
		
		Product mockproduct = new Product("test8", "OnePlus9Pro", 70, "tesdy@gmail.com");
		
		Product productToBeUpdated = new Product("test9", "OnePlus10Pro", 70, "tead@gmail.com");
		productToBeUpdated.setBaggageId(50);
		
		
		mockproduct.setBaggageId(50);
		// Prepare Mock Service Method
		
		doReturn(Optional.of(mockproduct)).when(repository).findById(50);
		
		doReturn(mockproduct).when(repository).save(ArgumentMatchers.any());
		
		// Perform GET Request
		
		mockMvc.perform(put("/api/v1/products/{id}",50)
		// Validate Status should be 200 OK and JSON response received
		
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(new ObjectMapper().writeValueAsString(productToBeUpdated)))
		
		
		// Validate Response Body
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.baggageId", is(50)))
		.andExpect(jsonPath("$.firstName", is("test9")))
		.andExpect(jsonPath("$.pnr", is("OnePlus10Pro")))
		.andExpect(jsonPath("$.seatno", is(70)))
		.andExpect(jsonPath("$.email", is("tead@gmail.com")));		
		
	}
	

}
