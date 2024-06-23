package com.examples.app.basics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

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
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	http.authorizeHttpRequests(requests -> requests
			.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
			.authenticated()
			.requestMatchers("/notices", "/contact").permitAll())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
	return http.build();
  }
}
