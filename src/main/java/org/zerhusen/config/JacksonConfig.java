package org.zerhusen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.jackson.ObjectMapperCustomizer;
import jakarta.inject.Singleton;

/**
 * Renders JSON the way the previous stack did: indented output, as configured by
 * {@code jackson.serialization.INDENT_OUTPUT}.
 */
@Singleton
public class JacksonConfig implements ObjectMapperCustomizer {

   @Override
   public void customize(ObjectMapper objectMapper) {
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
   }
}
