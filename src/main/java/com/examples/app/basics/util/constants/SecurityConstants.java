package com.examples.app.basics.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityConstants {

  public static final String[] SECURED_PATHS = {
	  "/myAccount",
	  "/myBalance",
	  "/myLoans",
	  "/myCards"
  };

  public static final String[] PUBLIC_PATHS = {
	  "/notices",
	  "/contact",
	  "/error",
	  "/register",
	  "/h2-console/**"
  };
}
