package com.myapp.spring.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.spring.model.Product;

// Annotation is to identify that this is spring managed bean
// This is a data repository class 

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	// select * from products where price> 
	Optional<List<Product>> findBySeatnoGreaterThanEqual(Integer seatno);
	
	Optional<List<Product>> findByFirstNameOrSeatno(String firstName,Integer seatno);

	Optional<List<Product>> findByFirstNameLike(String firstName);
	
	Optional<List<Product>> findBySeatnoIn(Collection<Integer> seatnos);
	
	Optional<List<Product>> findByFirstNameIgnoreCase(String firstName);
	
	
}