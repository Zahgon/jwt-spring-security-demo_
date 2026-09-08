package org.zerhusen;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.zerhusen.security.jwt.TokenProvider;
import org.zerhusen.security.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Counterpart of the Spring {@code contextLoads} smoke test: it proves the container came
 * up and that the beans the application is built from were actually produced.
 */
@QuarkusTest
class JwtDemoApplicationTest {

   @Inject
   TokenProvider tokenProvider;

   @Inject
   UserRepository userRepository;

   @Test
   void contextLoads() {
      // just test if the application context loads
      assertThat(tokenProvider).isNotNull();
      assertThat(userRepository).isNotNull();
   }
}
