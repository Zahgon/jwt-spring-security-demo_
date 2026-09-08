package org.zerhusen.security.jwt;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.zerhusen.config.WebSecurityConfig;
import org.zerhusen.security.SecurityContextHolder;

/**
 * Installs {@link JWTFilter} in front of the resource layer and hands the
 * authenticated request over to the access rules declared by
 * {@link WebSecurityConfig}.
 *
 * <p>The filter is pre-matching so that a request for an unmapped path is
 * rejected by the security layer rather than answered with a 404, which is what
 * the servlet filter chain did.</p>
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
@ApplicationScoped
public class JWTConfigurer implements ContainerRequestFilter {

   @Inject
   JWTFilter jwtFilter;

   @Inject
   WebSecurityConfig webSecurityConfig;

   @Override
   public void filter(ContainerRequestContext requestContext) throws IOException {
      SecurityContextHolder.clearContext();

      String path = normalise(requestContext.getUriInfo().getPath());
      String method = requestContext.getMethod();

      if (webSecurityConfig.isIgnored(method, path)) {
         return;
      }

      jwtFilter.doFilter(requestContext, path);

      Response rejection = webSecurityConfig.authorize(path);
      if (rejection != null) {
         requestContext.abortWith(rejection);
      }
   }

   private static String normalise(String path) {
      if (path == null || path.isEmpty()) {
         return "/";
      }
      return path.startsWith("/") ? path : "/" + path;
   }
}
