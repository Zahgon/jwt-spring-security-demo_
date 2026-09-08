package org.zerhusen.security;

/**
 * Thrown when an authenticated caller lacks the authority a route requires.
 */
public class AccessDeniedException extends RuntimeException {

   private static final long serialVersionUID = 3084677305709966761L;

   public AccessDeniedException(String message) {
      super(message);
   }
}
