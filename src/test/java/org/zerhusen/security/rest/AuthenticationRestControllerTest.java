package org.zerhusen.security.rest;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.zerhusen.util.AbstractRestControllerTest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

@QuarkusTest
class AuthenticationRestControllerTest extends AbstractRestControllerTest {

   @Test
   void successfulAuthenticationWithUser() {
      RestAssured.given()
         .contentType(ContentType.JSON)
         .body("{\"password\": \"password\", \"username\": \"user\"}")
         .when()
         .post("/api/authenticate")
         .then()
         .statusCode(200)
         .body(containsString("id_token"));
   }

   @Test
   void successfulAuthenticationWithAdmin() {
      RestAssured.given()
         .contentType(ContentType.JSON)
         .body("{\"password\": \"admin\", \"username\": \"admin\"}")
         .when()
         .post("/api/authenticate")
         .then()
         .statusCode(200)
         .body(containsString("id_token"));
   }

   @Test
   void unsuccessfulAuthenticationWithDisabled() {
      RestAssured.given()
         .contentType(ContentType.JSON)
         .body("{\"password\": \"password\", \"username\": \"disabled\"}")
         .when()
         .post("/api/authenticate")
         .then()
         .statusCode(401)
         .body(not(containsString("id_token")));
   }

   @Test
   void unsuccessfulAuthenticationWithWrongPassword() {
      RestAssured.given()
         .contentType(ContentType.JSON)
         .body("{\"password\": \"wrong\", \"username\": \"user\"}")
         .when()
         .post("/api/authenticate")
         .then()
         .statusCode(401)
         .body(not(containsString("id_token")));
   }

   @Test
   void unsuccessfulAuthenticationWithNotExistingUser() {
      RestAssured.given()
         .contentType(ContentType.JSON)
         .body("{\"password\": \"password\", \"username\": \"not_existing\"}")
         .when()
         .post("/api/authenticate")
         .then()
         .statusCode(401)
         .body(not(containsString("id_token")));
   }
}
