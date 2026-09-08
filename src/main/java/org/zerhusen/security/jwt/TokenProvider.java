package org.zerhusen.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.zerhusen.security.Authentication;
import org.zerhusen.security.UserDetails;

@ApplicationScoped
public class TokenProvider {

   private static final Logger LOG = Logger.getLogger(TokenProvider.class);

   private static final String AUTHORITIES_KEY = "auth";

   private final String base64Secret;

   private final long tokenValidityInMilliseconds;

   private final long tokenValidityInMillisecondsForRememberMe;

   private Key key;

   @Inject
   public TokenProvider(
      @ConfigProperty(name = "jwt.base64-secret") String base64Secret,
      @ConfigProperty(name = "jwt.token-validity-in-seconds") long tokenValidityInSeconds,
      @ConfigProperty(name = "jwt.token-validity-in-seconds-for-remember-me") long tokenValidityInSecondsForRememberMe) {
      this.base64Secret = base64Secret;
      this.tokenValidityInMilliseconds = 1000 * tokenValidityInSeconds;
      this.tokenValidityInMillisecondsForRememberMe = 1000 * tokenValidityInSecondsForRememberMe;
   }

   @PostConstruct
   public void init() {
      byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   public String createToken(Authentication authentication, boolean rememberMe) {
      String authorities = authentication.getAuthorities().stream()
         .collect(Collectors.joining(","));

      long now = new Date().getTime();
      Date validity = rememberMe
         ? new Date(now + this.tokenValidityInMillisecondsForRememberMe)
         : new Date(now + this.tokenValidityInMilliseconds);

      return Jwts.builder()
         .setSubject(authentication.getName())
         .claim(AUTHORITIES_KEY, authorities)
         .signWith(key, SignatureAlgorithm.HS512)
         .setExpiration(validity)
         .compact();
   }

   public Authentication getAuthentication(String token) {
      Claims claims = Jwts.parserBuilder()
         .setSigningKey(key)
         .build()
         .parseClaimsJws(token)
         .getBody();

      Set<String> authorities = Arrays.stream(String.valueOf(claims.get(AUTHORITIES_KEY)).split(","))
         .filter(authority -> !authority.isEmpty())
         .collect(Collectors.toCollection(LinkedHashSet::new));

      UserDetails principal = new UserDetails(claims.getSubject(), "", authorities);

      return new Authentication(principal, token, authorities);
   }

   public boolean validateToken(String authToken) {
      try {
         Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
         return true;
      } catch (SecurityException | MalformedJwtException e) {
         LOG.info("Invalid JWT signature.");
         LOG.trace("Invalid JWT signature trace: " + e);
      } catch (ExpiredJwtException e) {
         LOG.info("Expired JWT token.");
         LOG.trace("Expired JWT token trace: " + e);
      } catch (UnsupportedJwtException e) {
         LOG.info("Unsupported JWT token.");
         LOG.trace("Unsupported JWT token trace: " + e);
      } catch (IllegalArgumentException | JwtException e) {
         LOG.info("JWT token compact of handler are invalid.");
         LOG.trace("JWT token compact of handler are invalid trace: " + e);
      }
      return false;
   }
}
