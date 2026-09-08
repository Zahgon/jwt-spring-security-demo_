package org.zerhusen.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

/**
 * Returns a 403 error code (Forbidden) to the client.
 */
@ApplicationScoped
public class JwtAccessDeniedHandler {

   public static final String DEFAULT_MESSAGE = "Access is denied";

   public Response handle(String path, String message) {
      ErrorResponse body = new ErrorResponse(
         Response.Status.FORBIDDEN.getStatusCode(), "Forbidden", message, path);
      return Response.status(Response.Status.FORBIDDEN)
         .entity(body)
         .type(MediaTypes.APPLICATION_JSON_UTF8)
         .build();
   }
}
