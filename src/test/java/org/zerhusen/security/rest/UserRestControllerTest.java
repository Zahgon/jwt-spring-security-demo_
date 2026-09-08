package org.zerhusen.security.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.zerhusen.util.AbstractRestControllerTest;
import org.zerhusen.util.LogInUtils;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

@QuarkusTest
class UserRestControllerTest extends AbstractRestControllerTest {

   @Test
   void getActualUserForUserWithToken() {
      String token = LogInUtils.getTokenForLogin("user", "password");

      RestAssured.given()
         .header("Authorization", "Bearer " + token)
         .when()
         .get("/api/user")
         .then()
         .statusCode(200)
         .body("username", equalTo("user"))
         .body("firstname", equalTo("user"))
         .body("lastname", equalTo("user"))
         .body("email", equalTo("enabled@user.com"))
         .body("authorities.name", hasItem("ROLE_USER"));
   }

   @Test
   void getActualUserForUserWithoutToken() {
      RestAssured.given()
         .when()
         .get("/api/user")
         .then()
         .statusCode(401);
   }
}
