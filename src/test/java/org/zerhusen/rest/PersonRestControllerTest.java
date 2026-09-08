package org.zerhusen.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.zerhusen.util.AbstractRestControllerTest;
import org.zerhusen.util.LogInUtils;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class PersonRestControllerTest extends AbstractRestControllerTest {

   @Test
   void getPersonForUser() {
      String token = LogInUtils.getTokenForLogin("user", "password");

      assertSuccessfulPersonRequest(token);
   }

   @Test
   void getPersonForAdmin() {
      String token = LogInUtils.getTokenForLogin("admin", "admin");

      assertSuccessfulPersonRequest(token);
   }

   @Test
   void getPersonForAnonymous() {
      RestAssured.given()
         .when()
         .get("/api/person")
         .then()
         .statusCode(401);
   }

   private void assertSuccessfulPersonRequest(String token) {
      RestAssured.given()
         .header("Authorization", "Bearer " + token)
         .when()
         .get("/api/person")
         .then()
         .statusCode(200)
         .body("name", equalTo("John Doe"))
         .body("email", equalTo("john.doe@test.org"));
   }
}
