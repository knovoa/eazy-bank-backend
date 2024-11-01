package com.examples.app.basics.repository;

import com.examples.app.basics.model.Customer;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
  Optional<Customer> findByEmail(String email);
}
