package com.examples.app.basics.business.impl;

import com.examples.app.basics.model.Customer;
import com.examples.app.basics.repository.CustomerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EazyBankUserDetailsServiceImpl implements UserDetailsService {

  private final CustomerRepository customerRepository;

  /**
   * Load user by username method.
   *
   * @param username {@link String}
   * @return {@link UserDetails}
   * @throws UsernameNotFoundException If given username not found
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	Customer customer = customerRepository.findByEmail(username).orElseThrow(() ->
		new UsernameNotFoundException("User details not found for the user: " + username));
	List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));
	return new User(customer.getEmail(), customer.getPassword(), authorities);
  }
}
