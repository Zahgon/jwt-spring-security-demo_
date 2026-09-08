package org.zerhusen.util;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

/**
 * Obtains a real access token by driving the public authentication endpoint, exactly as a
 * client would. Ported from the {@code MockMvc} based helper of the Spring project.
 */
public final class LogInUtils {

   private LogInUtils() {
      // utility class
   }

   public static String getTokenForLogin(String username, String password) {
      AuthenticationResponse response = RestAssured.given()
         .contentType(ContentType.JSON)
         .body("{\"password\": \"" + password + "\", \"username\": \"" + username + "\"}")
         .when()
         .post("/api/authenticate")
         .then()
         .extract()
         .as(AuthenticationResponse.class);

      return response.getIdToken();
   }

   public static String bearer(String username, String password) {
      return "Bearer " + getTokenForLogin(username, password);
   }

   @JsonIgnoreProperties(ignoreUnknown = true)
   private static class AuthenticationResponse {

      @JsonAlias("id_token")
      private String idToken;

      public String getIdToken() {
         return idToken;
      }

      public void setIdToken(String idToken) {
         this.idToken = idToken;
      }
   }
}
