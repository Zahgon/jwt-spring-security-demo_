package org.zerhusen.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * Verifies a username / password pair and turns it into an {@link Authentication}.
 * An unknown user and a wrong password both surface as bad credentials so the
 * endpoint never reveals which of the two occurred.
 */
@ApplicationScoped
public class AuthenticationManager {

   private final UserModelDetailsService userDetailsService;

   private final PasswordEncoder passwordEncoder;

   @Inject
   public AuthenticationManager(UserModelDetailsService userDetailsService,
                                PasswordEncoder passwordEncoder) {
      this.userDetailsService = userDetailsService;
      this.passwordEncoder = passwordEncoder;
   }

   public Authentication authenticate(String username, String password) {
      UserDetails userDetails;
      try {
         userDetails = userDetailsService.loadUserByUsername(username);
      } catch (UsernameNotFoundException e) {
         throw new BadCredentialsException("Bad credentials");
      }

      if (!passwordEncoder.matches(password, userDetails.getPassword())) {
         throw new BadCredentialsException("Bad credentials");
      }

      return new Authentication(userDetails, password, userDetails.getAuthorities());
   }
}
