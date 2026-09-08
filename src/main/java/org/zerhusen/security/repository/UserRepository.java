package org.zerhusen.security.repository;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.zerhusen.security.model.User;

/**
 * Panache repository for the {@link User} entity.
 *
 * <p>Both finders join the authorities eagerly, which is what the entity graph
 * on the JPA repository used to do.</p>
 */
@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {

   private static final String WITH_AUTHORITIES =
      "select distinct u from User u left join fetch u.authorities";

   public Optional<User> findOneWithAuthoritiesByUsername(String username) {
      return find(WITH_AUTHORITIES + " where u.username = ?1", username)
         .firstResultOptional();
   }

   public Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email) {
      return find(WITH_AUTHORITIES + " where lower(u.email) = lower(?1)", email)
         .firstResultOptional();
   }
}
