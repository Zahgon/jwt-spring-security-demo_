package org.zerhusen.security;

/**
 * The media types the application answers with. The explicit charset keeps the
 * response headers byte-identical to the ones the servlet stack produced.
 */
public final class MediaTypes {

   public static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

   private MediaTypes() {
   }
}
