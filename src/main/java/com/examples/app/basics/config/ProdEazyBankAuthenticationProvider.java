package com.examples.app.basics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("prod")
@Component
@RequiredArgsConstructor
public class ProdEazyBankAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  /**
   * @param authentication {@link Authentication}
   * @return {@link Authentication}
   * @throws AuthenticationException when authentication fails
   */
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	String username = authentication.getName();
	String pwd = authentication.getCredentials().toString();
	UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	if (passwordEncoder.matches(pwd, userDetails.getPassword())) {
	  return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());
	} else {
	  throw new BadCredentialsException("Invalid password!!!");
	}
  }

  /**
   * @param authentication {@link Class}
   * @return {@link boolean}
   */
  @Override
  public boolean supports(Class<?> authentication) {
	return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
