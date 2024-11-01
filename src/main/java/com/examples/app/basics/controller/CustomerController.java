package com.examples.app.basics.controller;

import com.examples.app.basics.model.Customer;
import com.examples.app.basics.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerRepository customerRepository;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody Customer customer) {
	try {
	  String hashPasword = passwordEncoder.encode(customer.getPassword());
	  customer.setPassword(hashPasword);
	  customer.setRole("read");
	  Customer savedCustomer = customerRepository.save(customer);
	  return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
	} catch (Exception e) {
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
		  .body("An Exception occurred: " + e.getMessage());
	}
  }
}
