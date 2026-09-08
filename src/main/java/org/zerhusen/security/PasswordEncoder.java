package org.zerhusen.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * BCrypt password encoder. The hashes shipped in {@code import.sql} were produced
 * with cost factor 8; BCrypt reads the cost from the hash itself, so existing
 * rows keep verifying while newly encoded passwords use the default cost.
 */
@ApplicationScoped
public class PasswordEncoder {

   private static final int DEFAULT_COST = 10;

   public String encode(CharSequence rawPassword) {
      return BCrypt.withDefaults().hashToString(DEFAULT_COST, rawPassword.toString().toCharArray());
   }

   public boolean matches(CharSequence rawPassword, String encodedPassword) {
      if (rawPassword == null || encodedPassword == null || encodedPassword.isEmpty()) {
         return false;
      }
      return BCrypt.verifyer()
         .verify(rawPassword.toString().toCharArray(), encodedPassword.toCharArray())
         .verified;
   }
}
