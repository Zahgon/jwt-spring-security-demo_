package org.zerhusen.security;

/**
 * Thrown when the login handed to {@link UserModelDetailsService} matches no row
 * in the user table.
 */
public class UsernameNotFoundException extends AuthenticationException {

   private static final long serialVersionUID = 2062243503105228064L;

   public UsernameNotFoundException(String message) {
      super(message);
   }
}
