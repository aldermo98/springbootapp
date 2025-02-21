package com.springboot.backend.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.backend.model.Customer;
import com.springboot.backend.repository.CustomerRepository;
import com.springboot.backend.repository.UserRepository;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/customer")
	public void postCustomer(@RequestBody Customer customer) {
		customerRepository.save(customer);
	}
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
	
		return customerRepository.findAll();
	}
	
	
	@GetMapping("/customer/{id}")
	public Customer getCustomerById(@PathVariable("id") Long id) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent())
			return optional.get();
		throw new RuntimeException("ID is invalid");
	}
	
	@PutMapping("/customer/{id}")
	public Customer updateCustomer(@PathVariable("id") Long id,
			@RequestBody Customer newCustomer) {
		Optional<Customer> optional = customerRepository.findById(id);
		if (optional.isPresent()) {
			Customer existingCustomer = optional.get();
			existingCustomer.setName(newCustomer.getName());
			existingCustomer.setPassword(newCustomer.getPassword());
			existingCustomer.setBalance(newCustomer.getBalance());
			return customerRepository.save(existingCustomer);
		}
		else
			throw new RuntimeException("ID is invalid");
		
	}
	
	@PutMapping("/customer/{id}/updateBalance/{price}")
	public void customerUpdateBalance(@PathVariable("id") Long id,
			@PathVariable("price") double price) {
		customerRepository.updateBalance(id, price);
		
	}
	
	@DeleteMapping("/customer/{id}")
	public void deleteCustomer(@PathVariable("id") Long id) {
		customerRepository.deleteById(id);
		userRepository.deleteById(id-1);
	}
	
	//TO-DO
	/*
	@GetMapping("/customerLogin")
	public void customerLogin(){
	
	}
		  
	 */
	
}
