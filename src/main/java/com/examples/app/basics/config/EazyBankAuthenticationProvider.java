package com.examples.app.basics.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Profile("!prod")
@Component
@RequiredArgsConstructor
public class EazyBankAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;

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
	return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());
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