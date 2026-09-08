####
# This Dockerfile is used in order to build a container that runs the Quarkus
# application in JVM mode.
#
# Build the image with:
#
#   mvn package
#   docker build -f src/main/docker/Dockerfile -t hubae/jwt-spring-security-demo .
#
# Run the container with:
#
#   docker run -i --rm -p 8080:8080 hubae/jwt-spring-security-demo
#
####
FROM eclipse-temurin:21-jre-alpine

ENV LANGUAGE='en_US:en'

WORKDIR /usr/share

COPY target/quarkus-app/lib/ /usr/share/quarkus-app/lib/
COPY target/quarkus-app/*.jar /usr/share/quarkus-app/
COPY target/quarkus-app/app/ /usr/share/quarkus-app/app/
COPY target/quarkus-app/quarkus/ /usr/share/quarkus-app/quarkus/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/usr/share/quarkus-app/quarkus-run.jar"]
