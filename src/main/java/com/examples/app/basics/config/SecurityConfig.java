package com.examples.app.basics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class SecurityConfig {

  /**
   * Configures the security filter chain for the application.
   *
   * @param http the HttpSecurity object used to configure the filter chain
   * @return the configured SecurityFilterChain
   * @throws Exception if there is an error configuring the filter chain
   */
  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(requests -> requests
			.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
			.authenticated()
			.requestMatchers("/notices", "/contact").permitAll())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
	return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
	UserDetails user = User
		.withUsername("user")
		.password("{noop}12345")
		.authorities("read")
		.build();
	UserDetails admin = User
		.withUsername("admin")
		.password("{bcrypt}$2y$10$Os.bo9XLB2ktFb4rpsP1oufg81ZLRLYZI3YuNre5yiFkoE8yTyPfS")
		.authorities("admin")
		.build();
	return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

  @Bean
  public CompromisedPasswordChecker compromisedPasswordChecker() {
	return new HaveIBeenPwnedRestApiPasswordChecker();
  }
}
