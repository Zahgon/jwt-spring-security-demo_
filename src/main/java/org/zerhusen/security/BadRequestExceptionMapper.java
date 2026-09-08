package org.zerhusen.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * A body that fails bean validation is rejected with 400, matching the status the
 * previous stack returned for an invalid login payload.
 */
@Provider
@ApplicationScoped
public class BadRequestExceptionMapper implements ExceptionMapper<ValidationException> {

   @Context
   UriInfo uriInfo;

   @Override
   public Response toResponse(ValidationException exception) {
      String path = uriInfo.getPath();
      ErrorResponse body = new ErrorResponse(
         Response.Status.BAD_REQUEST.getStatusCode(),
         "Bad Request",
         exception.getMessage(),
         path.startsWith("/") ? path : "/" + path);
      return Response.status(Response.Status.BAD_REQUEST)
         .entity(body)
         .type(MediaTypes.APPLICATION_JSON_UTF8)
         .build();
   }
}
