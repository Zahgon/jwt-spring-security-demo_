# JWT Quarkus Security Demo

![Screenshot from running application](etc/screenshot-jwt-spring-security-demo.png?raw=true "Screenshot JWT Quarkus Security Demo")

## About
This is a demo for using **[JWT (JSON Web Token)](https://jwt.io)** with **[Quarkus](https://quarkus.io)**.

It is a framework migration of the original `jwt-spring-security-demo`: the HTTP contract,
the domain model, the database schema, the seed data and the JavaScript client are unchanged.
Only the framework layer was translated — Spring MVC became JAX-RS (RESTEasy), Spring Data JPA
became Hibernate ORM with Panache, the Spring Security filter chain became a JAX-RS
`@PreMatching` `ContainerRequestFilter`, and Spring's bean container became CDI (ArC).

## Requirements
This demo is built with Maven 3.9.x and Java 17+ (the build and the test suite run on Java 21).

## Usage
Start the application in development mode with `mvn quarkus:dev`, or package and run it:

```
mvn package
java -jar target/quarkus-app/quarkus-run.jar
```

The application is running at [http://localhost:8080](http://localhost:8080).

## Backend
There are three user accounts present to demonstrate the different levels of access to the endpoints in
the API and the different authorization exceptions:
```
Admin - admin:admin
User - user:password
Disabled - disabled:password (this user is deactivated)
```

There are four endpoints that are reasonable for the demo:
```
/api/authenticate - authentication endpoint with unrestricted access
/api/user - returns detail information for an authenticated user (a valid JWT token must be present in the request header)
/api/person - an example endpoint that is restricted to authorized users with the authority 'ROLE_USER' (a valid JWT token must be present in the request header)
/api/hiddenmessage - an example endpoint that is restricted to authorized users with the authority 'ROLE_ADMIN' (a valid JWT token must be present in the request header)
```

A request without a token is answered with `401`, a request with a valid token but the wrong
authority with `403` — the same behaviour the Spring version had.

## Frontend
The small Javascript client from the original demo is unchanged. Quarkus serves static resources from
`src/main/resources/META-INF/resources`, so the client now lives at
[/src/main/resources/META-INF/resources/js/client.js](/src/main/resources/META-INF/resources/js/client.js).

### Generating password hashes for new users

Passwords are hashed with [bcrypt](https://en.wikipedia.org/wiki/Bcrypt). You can generate your hashes with this simple
tool: [Bcrypt Generator](https://www.bcrypt-generator.com). The hashes from the original demo still work — the
migration kept the same encoder and the same cost factor.

### Using another database

This demo uses an embedded H2 database. To connect to another database, change the datasource keys in
*application.properties* in the resource directory. Here is an example for a MySQL DB:

```
quarkus.datasource.db-kind=mysql
quarkus.datasource.username=myUser
quarkus.datasource.password=myPassword
quarkus.datasource.jdbc.url=jdbc:mysql://localhost/myDatabase
# possible values: none | create | drop-and-create | drop | update | validate
quarkus.hibernate-orm.database.generation=drop-and-create
```

*Hint: For other databases like MySQL sequences don't work for ID generation. So you have to change the GenerationType in the entity beans to 'AUTO' or 'IDENTITY'.*

You can find a reference of all application properties [here](https://quarkus.io/guides/all-config).

## Docker
Package the application first, then build the JVM image:

```
mvn package
docker build -f src/main/docker/Dockerfile -t hubae/jwt-spring-security-demo .
docker run -i --rm -p 8080:8080 hubae/jwt-spring-security-demo
```

## Author

**Stephan Zerhusen**

* https://twitter.com/stzerhus
* https://github.com/szerhusenBC

## Copyright and license

The code is released under the [MIT license](LICENSE?raw=true).
