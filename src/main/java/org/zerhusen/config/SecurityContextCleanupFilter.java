package org.zerhusen.config;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import org.zerhusen.security.MediaTypes;
import org.zerhusen.security.SecurityContextHolder;

/**
 * Releases the thread bound security context once the response has been written
 * and pins the charset on JSON responses, both of which the servlet container
 * used to take care of.
 */
@Provider
@Priority(Priorities.USER)
@ApplicationScoped
public class SecurityContextCleanupFilter implements ContainerResponseFilter {

   @Override
   public void filter(ContainerRequestContext requestContext,
                      ContainerResponseContext responseContext) throws IOException {
      MediaType mediaType = responseContext.getMediaType();
      if (mediaType != null
         && MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType)
         && mediaType.getParameters().get(MediaType.CHARSET_PARAMETER) == null) {
         responseContext.getHeaders().putSingle("Content-Type", MediaTypes.APPLICATION_JSON_UTF8);
      }
      SecurityContextHolder.clearContext();
   }
}
