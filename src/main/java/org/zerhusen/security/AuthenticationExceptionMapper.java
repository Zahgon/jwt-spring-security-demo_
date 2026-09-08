package org.zerhusen.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Renders every authentication failure raised by a resource method with the same
 * 401 envelope the security layer produces for an anonymous request.
 */
@Provider
@ApplicationScoped
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

   @Context
   UriInfo uriInfo;

   @Inject
   JwtAuthenticationEntryPoint authenticationErrorHandler;

   @Override
   public Response toResponse(AuthenticationException exception) {
      return authenticationErrorHandler.commence(path(), exception.getMessage());
   }

   private String path() {
      String path = uriInfo.getPath();
      return path.startsWith("/") ? path : "/" + path;
   }
}
