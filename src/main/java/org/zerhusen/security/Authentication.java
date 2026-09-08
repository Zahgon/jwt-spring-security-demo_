package org.zerhusen.security;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The authenticated caller of the current request together with the authorities
 * that were granted to it.
 */
public class Authentication {

   private final Object principal;

   private final Object credentials;

   private final Set<String> authorities;

   public Authentication(Object principal, Object credentials) {
      this(principal, credentials, Collections.emptyList());
   }

   public Authentication(Object principal, Object credentials, Collection<String> authorities) {
      this.principal = principal;
      this.credentials = credentials;
      this.authorities = new LinkedHashSet<>(authorities);
   }

   public Object getPrincipal() {
      return principal;
   }

   public Object getCredentials() {
      return credentials;
   }

   public Set<String> getAuthorities() {
      return authorities;
   }

   public String getName() {
      if (principal instanceof UserDetails) {
         return ((UserDetails) principal).getUsername();
      }
      return String.valueOf(principal);
   }

   public boolean hasAuthority(String authority) {
      return authorities.contains(authority);
   }
}
