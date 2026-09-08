package org.zerhusen.security;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The error envelope every rejected request is rendered with. It reproduces the
 * body the servlet container used to emit for {@code sendError}, so clients that
 * parse it keep working unchanged.
 */
@JsonPropertyOrder({ "timestamp", "status", "error", "message", "path" })
public class ErrorResponse {

   private static final DateTimeFormatter TIMESTAMP =
      DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

   private final String timestamp;

   private final int status;

   private final String error;

   private final String message;

   private final String path;

   public ErrorResponse(int status, String error, String message, String path) {
      this.timestamp = OffsetDateTime.now(ZoneOffset.UTC).format(TIMESTAMP);
      this.status = status;
      this.error = error;
      this.message = message == null ? "" : message;
      this.path = path;
   }

   public String getTimestamp() {
      return timestamp;
   }

   public int getStatus() {
      return status;
   }

   public String getError() {
      return error;
   }

   public String getMessage() {
      return message;
   }

   public String getPath() {
      return path;
   }
}
