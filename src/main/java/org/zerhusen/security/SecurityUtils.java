package org.zerhusen.security;

import java.util.Optional;

import org.jboss.logging.Logger;

/**
 * Utility class for the security layer.
 */
public final class SecurityUtils {

   private static final Logger LOG = Logger.getLogger(SecurityUtils.class);

   private SecurityUtils() {
   }

   /**
    * Get the login of the current user.
    *
    * @return the login of the current user
    */
   public static Optional<String> getCurrentUsername() {
      Authentication authentication = SecurityContextHolder.getAuthentication();
      if (authentication == null) {
         LOG.debug("no authentication in security context found");
         return Optional.empty();
      }

      String username = null;
      Object principal = authentication.getPrincipal();
      if (principal instanceof UserDetails) {
         username = ((UserDetails) principal).getUsername();
      } else if (principal instanceof String) {
         username = (String) principal;
      }

      LOG.debugf("found username '%s' in security context", username);
      return Optional.ofNullable(username);
   }
}
