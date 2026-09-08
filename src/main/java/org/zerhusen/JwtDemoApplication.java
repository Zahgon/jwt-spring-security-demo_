package org.zerhusen;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

/**
 * Entry point of the demo application.
 *
 * @author Stephan Zerhusen
 */
@QuarkusMain
public class JwtDemoApplication implements QuarkusApplication {

   @Override
   public int run(String... args) {
      Quarkus.waitForExit();
      return 0;
   }

   public static void main(String[] args) {
      Quarkus.run(JwtDemoApplication.class, args);
   }
}
