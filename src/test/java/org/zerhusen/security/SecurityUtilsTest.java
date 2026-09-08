package org.zerhusen.security;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Plain unit test - no container is needed, exactly as in the Spring project where this
 * test did not carry {@code @SpringBootTest} either.
 */
class SecurityUtilsTest {

   @AfterEach
   void tearDown() {
      SecurityContextHolder.clearContext();
   }

   @Test
   void getCurrentUsername() {
      SecurityContextHolder.clearContext();
      SecurityContextHolder.setAuthentication(new Authentication("admin", "admin"));

      Optional<String> username = SecurityUtils.getCurrentUsername();

      assertThat(username).contains("admin");
   }

   @Test
   void getCurrentUsernameForNoAuthenticationInContext() {
      SecurityContextHolder.clearContext();

      Optional<String> username = SecurityUtils.getCurrentUsername();

      assertThat(username).isEmpty();
   }
}
