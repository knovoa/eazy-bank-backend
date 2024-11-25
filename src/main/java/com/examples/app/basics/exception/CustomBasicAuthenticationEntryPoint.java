package com.examples.app.basics.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * @param request {@link HttpServletRequest}
   * @param response {@link HttpServletResponse}
   * @param authException {@link AuthenticationException}
   * @throws IOException if an I/O error occurs
   */
  @Override
  public void commence(HttpServletRequest request,
					   HttpServletResponse response,
					   AuthenticationException authException) throws IOException {
	response.setHeader("eazybank-error-reason", "Authentication failed");
	response.setStatus(HttpStatus.UNAUTHORIZED.value());
	response.setContentType("application/json;charset=UTF-8");

	LocalDateTime timestamp = LocalDateTime.now();
	String message = (authException != null && authException.getMessage() != null)
		? authException.getMessage()
		: "Unauthorized";
	String path = request.getRequestURI();

	String jsonResponse = String.format("{" +
			"\"timestamp\": \"%s\", " +
			"\"status\": %d, " +
			"\"error\": \"%s\", " +
			"\"message\": \"%s\", " +
			"\"path\": \"%s\"}",
		timestamp, HttpStatus.UNAUTHORIZED.value(),
		HttpStatus.UNAUTHORIZED.getReasonPhrase(), message, path);
	response.getWriter().write(jsonResponse);
  }
}
