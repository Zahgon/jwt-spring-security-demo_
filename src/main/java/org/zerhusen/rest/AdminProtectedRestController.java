package org.zerhusen.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
@ApplicationScoped
public class AdminProtectedRestController {

   @GET
   @Path("/hiddenmessage")
   @Produces(MediaType.APPLICATION_JSON)
   public Response getHiddenMessage() {
      return Response.ok(new HiddenMessage("this is a hidden message!")).build();
   }

   public static class HiddenMessage {

      private final String message;

      HiddenMessage(String message) {
         this.message = message;
      }

      public String getMessage() {
         return message;
      }
   }
}
