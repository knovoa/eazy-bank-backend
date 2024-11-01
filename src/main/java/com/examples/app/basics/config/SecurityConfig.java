package com.examples.app.basics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	http
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(requests -> requests
			.requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards")
			.authenticated()
			.requestMatchers("/notices", "/contact", "/error", "/register").permitAll())
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults());
	return http.build();
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
