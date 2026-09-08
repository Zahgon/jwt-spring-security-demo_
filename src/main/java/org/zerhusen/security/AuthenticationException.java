package org.zerhusen.security;

/**
 * Base type of every failure that stops a caller from being authenticated.
 */
public class AuthenticationException extends RuntimeException {

   private static final long serialVersionUID = -8886177424588488564L;

   public AuthenticationException(String message) {
      super(message);
   }

   public AuthenticationException(String message, Throwable cause) {
      super(message, cause);
   }
}
