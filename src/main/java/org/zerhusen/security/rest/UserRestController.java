package org.zerhusen.security.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.zerhusen.security.model.User;
import org.zerhusen.security.service.UserService;

@Path("/api")
@ApplicationScoped
public class UserRestController {

   private final UserService userService;

   @Inject
   public UserRestController(UserService userService) {
      this.userService = userService;
   }

   @GET
   @Path("/user")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getActualUser() {
      User user = userService.getUserWithAuthorities().orElseThrow();
      return Response.ok(user).build();
   }
}
