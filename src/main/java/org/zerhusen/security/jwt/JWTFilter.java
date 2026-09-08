package org.zerhusen.security.jwt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.jboss.logging.Logger;
import org.zerhusen.security.Authentication;
import org.zerhusen.security.SecurityContextHolder;

/**
 * Filters incoming requests and installs a security context if a header
 * corresponding to a valid user is found.
 */
@ApplicationScoped
public class JWTFilter {

   public static final String AUTHORIZATION_HEADER = "Authorization";

   private static final String BEARER_PREFIX = "Bearer ";

   private static final Logger LOG = Logger.getLogger(JWTFilter.class);

   private final TokenProvider tokenProvider;

   @Inject
   public JWTFilter(TokenProvider tokenProvider) {
      this.tokenProvider = tokenProvider;
   }

   public void doFilter(ContainerRequestContext requestContext, String requestUri) {
      String jwt = resolveToken(requestContext);
      if (hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
         Authentication authentication = this.tokenProvider.getAuthentication(jwt);
         SecurityContextHolder.setAuthentication(authentication);
         LOG.debugf("set Authentication to security context for '%s', uri: %s",
            authentication.getName(), requestUri);
      } else {
         LOG.debugf("no valid JWT token found, uri: %s", requestUri);
      }
   }

   private String resolveToken(ContainerRequestContext requestContext) {
      String bearerToken = requestContext.getHeaderString(AUTHORIZATION_HEADER);
      if (hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
         return bearerToken.substring(BEARER_PREFIX.length());
      }
      return null;
   }

   private static boolean hasText(String value) {
      return value != null && !value.trim().isEmpty();
   }
}
