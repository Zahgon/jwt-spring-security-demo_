package org.zerhusen.security;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The credentials and authorities of a user, as resolved from the database by
 * {@link UserModelDetailsService}.
 */
public class UserDetails {

   private final String username;

   private final String password;

   private final Set<String> authorities;

   public UserDetails(String username, String password, Collection<String> authorities) {
      this.username = username;
      this.password = password;
      this.authorities = new LinkedHashSet<>(authorities);
   }

   public String getUsername() {
      return username;
   }

   public String getPassword() {
      return password;
   }

   public Set<String> getAuthorities() {
      return authorities;
   }
}
