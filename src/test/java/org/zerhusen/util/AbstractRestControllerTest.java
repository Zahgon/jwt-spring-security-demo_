package org.zerhusen.util;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeEach;
import org.zerhusen.security.SecurityContextHolder;

/**
 * Shared bootstrap for every REST level test.
 *
 * <p>The Spring flavour of this class relied on {@code @SpringBootTest} plus
 * {@code @AutoConfigureMockMvc} to obtain a {@code MockMvc} handle. Quarkus boots the
 * real HTTP stack for {@code @QuarkusTest}, so the requests travel over a socket and
 * RestAssured is the client. What survives unchanged is the contract of this class:
 * every test starts from an empty security context.</p>
 */
public abstract class AbstractRestControllerTest {

   private static final int CLIENT_WAIT_MILLIS = 180_000;

   /**
    * Widens only how long the client waits, never what the assertions demand: the first
    * request of a run pays for lazy Hibernate and connection-pool initialisation, which
    * outlasts RestAssured's stock socket timeout on a loaded machine. The assignment is
    * repeated per test because the Quarkus test extension resets RestAssured between
    * methods.
    */
   @BeforeEach
   public void setUp() {
      RestAssured.config = RestAssuredConfig.config()
         .httpClient(HttpClientConfig.httpClientConfig()
            .setParam("http.connection.timeout", CLIENT_WAIT_MILLIS)
            .setParam("http.socket.timeout", CLIENT_WAIT_MILLIS));
      SecurityContextHolder.clearContext();
   }
}
