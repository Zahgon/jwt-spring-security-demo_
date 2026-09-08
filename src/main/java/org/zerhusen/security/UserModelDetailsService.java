package org.zerhusen.security;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import org.zerhusen.security.model.Authority;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Named("userDetailsService")
@ApplicationScoped
public class UserModelDetailsService {

   private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

   private final UserRepository userRepository;

   @Inject
   public UserModelDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional
   public UserDetails loadUserByUsername(final String login) {
      if (EMAIL.matcher(login).matches()) {
         return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(login)
            .map(user -> createSecurityUser(login, user))
            .orElseThrow(() -> new UsernameNotFoundException(
               "User with email " + login + " was not found in the database"));
      }

      String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
      return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
         .map(user -> createSecurityUser(lowercaseLogin, user))
         .orElseThrow(() -> new UsernameNotFoundException(
            "User " + lowercaseLogin + " was not found in the database"));
   }

   private UserDetails createSecurityUser(String lowercaseLogin, User user) {
      if (!user.isActivated()) {
         throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
      }
      Set<String> grantedAuthorities = user.getAuthorities().stream()
         .map(Authority::getName)
         .collect(Collectors.toCollection(java.util.LinkedHashSet::new));
      return new UserDetails(user.getUsername(), user.getPassword(), grantedAuthorities);
   }
}
