package org.zerhusen.security;

/**
 * Holds the {@link Authentication} of the request that the current thread is
 * serving. JAX-RS request filters, resource methods and services all run on the
 * same worker thread, so a thread local is the direct counterpart of the
 * security context the servlet stack used to carry around.
 */
public final class SecurityContextHolder {

   private static final ThreadLocal<Authentication> CONTEXT = new ThreadLocal<>();

   private SecurityContextHolder() {
   }

   public static Authentication getAuthentication() {
      return CONTEXT.get();
   }

   public static void setAuthentication(Authentication authentication) {
      CONTEXT.set(authentication);
   }

   public static void clearContext() {
      CONTEXT.remove();
   }
}
