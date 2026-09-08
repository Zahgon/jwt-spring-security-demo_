package org.zerhusen.security.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.zerhusen.security.model.Authority;

/**
 * Panache repository for the {@link Authority} entity.
 */
@ApplicationScoped
public class AuthorityRepository implements PanacheRepositoryBase<Authority, String> {
}
