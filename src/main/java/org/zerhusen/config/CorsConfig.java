package org.zerhusen.config;

import java.io.IOException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;

/**
 * Cross origin resource sharing for the {@code /api} namespace, the same scope
 * the servlet CORS filter was registered under. Credentials are allowed and the
 * requesting origin is echoed back, which is what
 * {@code CorsConfiguration.addAllowedOrigin("*")} degrades to once credentials
 * are in play.
 */
@Provider
@ApplicationScoped
public class CorsConfig implements ContainerResponseFilter {

   private static final String API_PREFIX = "/api";

   @Override
   public void filter(ContainerRequestContext requestContext,
                      ContainerResponseContext responseContext) throws IOException {
      String path = requestContext.getUriInfo().getPath();
      if (!path.startsWith("/")) {
         path = "/" + path;
      }
      if (!path.startsWith(API_PREFIX)) {
         return;
      }

      String origin = requestContext.getHeaderString("Origin");
      if (origin == null) {
         return;
      }

      MultivaluedMap<String, Object> headers = responseContext.getHeaders();
      headers.putSingle("Access-Control-Allow-Origin", origin);
      headers.putSingle("Access-Control-Allow-Credentials", "true");
      headers.putSingle("Access-Control-Allow-Headers", "*");
      headers.putSingle("Access-Control-Allow-Methods", "*");
      headers.putSingle("Vary", "Origin");
   }
}
