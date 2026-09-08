package org.zerhusen.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Renders an authorization failure raised inside a resource method with the same
 * 403 envelope the security layer produces.
 */
@Provider
@ApplicationScoped
public class AccessDeniedExceptionMapper implements ExceptionMapper<AccessDeniedException> {

   @Context
   UriInfo uriInfo;

   @Inject
   JwtAccessDeniedHandler jwtAccessDeniedHandler;

   @Override
   public Response toResponse(AccessDeniedException exception) {
      String path = uriInfo.getPath();
      return jwtAccessDeniedHandler.handle(
         path.startsWith("/") ? path : "/" + path, exception.getMessage());
   }
}
