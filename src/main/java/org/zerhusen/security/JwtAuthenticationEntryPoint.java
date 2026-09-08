package org.zerhusen.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

/**
 * Returns a 401 error code (Unauthorized) to the client.
 */
@ApplicationScoped
public class JwtAuthenticationEntryPoint {

   public static final String DEFAULT_MESSAGE =
      "Full authentication is required to access this resource";

   /**
    * Commences an authentication scheme.
    *
    * @param path    the path that was rejected
    * @param message the reason the request was rejected
    * @return the response that is sent back to the client
    */
   public Response commence(String path, String message) {
      ErrorResponse body = new ErrorResponse(
         Response.Status.UNAUTHORIZED.getStatusCode(), "Unauthorized", message, path);
      return Response.status(Response.Status.UNAUTHORIZED)
         .entity(body)
         .type(MediaTypes.APPLICATION_JSON_UTF8)
         .build();
   }
}
