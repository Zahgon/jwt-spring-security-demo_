package org.zerhusen.config;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.core.Response;
import org.zerhusen.security.Authentication;
import org.zerhusen.security.JwtAccessDeniedHandler;
import org.zerhusen.security.JwtAuthenticationEntryPoint;
import org.zerhusen.security.SecurityContextHolder;

/**
 * The access rules of the application.
 *
 * <p>This is the declarative security configuration the servlet filter chain used
 * to carry: a list of paths that bypass security altogether, one path that is
 * open to anonymous callers, two paths that each demand a single authority, and
 * a catch-all that requires an authenticated caller.</p>
 */
@ApplicationScoped
public class WebSecurityConfig {

   public static final String ROLE_USER = "ROLE_USER";

   public static final String ROLE_ADMIN = "ROLE_ADMIN";

   private static final String AUTHENTICATE_PATH = "/api/authenticate";

   private static final String[] IGNORED_EXACT = { "/", "/favicon.ico" };

   private static final String[] IGNORED_SUFFIXES = { ".html", ".css", ".js" };

   private static final String[] IGNORED_PREFIXES = { "/h2-console/" };

   private final Map<String, String> protectedPaths = new LinkedHashMap<>();

   private final JwtAuthenticationEntryPoint authenticationErrorHandler;

   private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

   @Inject
   public WebSecurityConfig(JwtAuthenticationEntryPoint authenticationErrorHandler,
                            JwtAccessDeniedHandler jwtAccessDeniedHandler) {
      this.authenticationErrorHandler = authenticationErrorHandler;
      this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
      this.protectedPaths.put("/api/person", ROLE_USER);
      this.protectedPaths.put("/api/hiddenmessage", ROLE_ADMIN);
   }

   /**
    * Paths that never reach the security layer: pre-flight requests and the
    * static assets that make up the single page front end.
    */
   public boolean isIgnored(String method, String path) {
      if (HttpMethod.OPTIONS.equals(method)) {
         return true;
      }
      for (String exact : IGNORED_EXACT) {
         if (exact.equals(path)) {
            return true;
         }
      }
      for (String suffix : IGNORED_SUFFIXES) {
         if (path.endsWith(suffix)) {
            return true;
         }
      }
      for (String prefix : IGNORED_PREFIXES) {
         if (path.startsWith(prefix)) {
            return true;
         }
      }
      return false;
   }

   /**
    * Applies the access rules to the current request.
    *
    * @return {@code null} when the request may proceed, otherwise the response
    *         that rejects it
    */
   public Response authorize(String path) {
      if (AUTHENTICATE_PATH.equals(path)) {
         return null;
      }

      Authentication authentication = SecurityContextHolder.getAuthentication();
      if (authentication == null) {
         return authenticationErrorHandler.commence(
            path, JwtAuthenticationEntryPoint.DEFAULT_MESSAGE);
      }

      String requiredAuthority = protectedPaths.get(path);
      if (requiredAuthority != null && !authentication.hasAuthority(requiredAuthority)) {
         return jwtAccessDeniedHandler.handle(path, JwtAccessDeniedHandler.DEFAULT_MESSAGE);
      }

      return null;
   }
}
