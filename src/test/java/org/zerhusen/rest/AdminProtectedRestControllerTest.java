package org.zerhusen.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.zerhusen.util.AbstractRestControllerTest;
import org.zerhusen.util.LogInUtils;

import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class AdminProtectedRestControllerTest extends AbstractRestControllerTest {

   @Test
   void getAdminProtectedGreetingForUser() {
      String token = LogInUtils.getTokenForLogin("user", "password");

      RestAssured.given()
         .header("Authorization", "Bearer " + token)
         .when()
         .get("/api/hiddenmessage")
         .then()
         .statusCode(403);
   }

   @Test
   void getAdminProtectedGreetingForAdmin() {
      String token = LogInUtils.getTokenForLogin("admin", "admin");

      RestAssured.given()
         .header("Authorization", "Bearer " + token)
         .when()
         .get("/api/hiddenmessage")
         .then()
         .statusCode(200)
         .body("message", equalTo("this is a hidden message!"));
   }

   @Test
   void getAdminProtectedGreetingForAnonymous() {
      RestAssured.given()
         .when()
         .get("/api/hiddenmessage")
         .then()
         .statusCode(401);
   }
}
