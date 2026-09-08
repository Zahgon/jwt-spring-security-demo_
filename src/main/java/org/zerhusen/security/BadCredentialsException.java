package org.zerhusen.security;

/**
 * Thrown when the presented password does not match the stored hash. The message
 * is deliberately identical for an unknown user and for a wrong password so that
 * the endpoint does not disclose which of the two happened.
 */
public class BadCredentialsException extends AuthenticationException {

   private static final long serialVersionUID = 4525104193200041545L;

   public BadCredentialsException(String message) {
      super(message);
   }
}
