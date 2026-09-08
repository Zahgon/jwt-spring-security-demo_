package org.zerhusen.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@ApplicationScoped
public class PersonRestController {

   @GET
   @Path("/person")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getPerson() {
      return Response.ok(new Person("John Doe", "john.doe@test.org")).build();
   }

   public static class Person {

      private final String name;

      private final String email;

      Person(String name, String email) {
         this.name = name;
         this.email = email;
      }

      public String getName() {
         return name;
      }

      public String getEmail() {
         return email;
      }
   }
}
