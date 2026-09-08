package org.zerhusen.security.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.zerhusen.security.Authentication;
import org.zerhusen.security.AuthenticationManager;
import org.zerhusen.security.SecurityContextHolder;
import org.zerhusen.security.jwt.JWTFilter;
import org.zerhusen.security.jwt.TokenProvider;
import org.zerhusen.security.rest.dto.LoginDto;

@Path("/api")
@ApplicationScoped
public class AuthenticationRestController {

   private final TokenProvider tokenProvider;

   private final AuthenticationManager authenticationManager;

   @Inject
   public AuthenticationRestController(TokenProvider tokenProvider,
                                       AuthenticationManager authenticationManager) {
      this.tokenProvider = tokenProvider;
      this.authenticationManager = authenticationManager;
   }

   @POST
   @Path("/authenticate")
   @Consumes(MediaType.APPLICATION_JSON)
   @Produces(MediaType.APPLICATION_JSON)
   public Response authorize(@Valid LoginDto loginDto) {
      Authentication authentication =
         authenticationManager.authenticate(loginDto.getUsername(), loginDto.getPassword());
      SecurityContextHolder.setAuthentication(authentication);

      boolean rememberMe = loginDto.isRememberMe() != null && loginDto.isRememberMe();
      String jwt = tokenProvider.createToken(authentication, rememberMe);

      return Response.ok(new JWTToken(jwt))
         .header(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt)
         .build();
   }

   /**
    * Object to return as body in JWT Authentication.
    */
   static class JWTToken {

      private String idToken;

      JWTToken(String idToken) {
         this.idToken = idToken;
      }

      @JsonProperty("id_token")
      String getIdToken() {
         return idToken;
      }

      void setIdToken(String idToken) {
         this.idToken = idToken;
      }
   }
}
