package com.examples.app.basics.config;

import static com.examples.app.basics.util.constants.SecurityConstants.PUBLIC_PATHS;
import static com.examples.app.basics.util.constants.SecurityConstants.SECURED_PATHS;

import com.examples.app.basics.exception.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Profile("!prod")
@Configuration
public class SecurityConfig {

  /**
   * Configures the security filter chain for the application.
   *
   * @param http {@link HttpSecurity} the HttpSecurity object used to configure the filter chain
   * @return {@link SecurityFilterChain} the configured SecurityFilterChain
   * @throws Exception if there is an error configuring the filter chain
   */
  @Bean
  public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
	http
		.requiresChannel(channel -> channel.anyRequest().requiresInsecure()) //Only HTTP
		.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
			.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
		.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(requests -> requests
			.requestMatchers(SECURED_PATHS).authenticated()
			.requestMatchers(PUBLIC_PATHS).permitAll())
		.formLogin(Customizer.withDefaults())
		//.httpBasic(Customizer.withDefaults());
		.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
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
