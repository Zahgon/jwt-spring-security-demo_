package org.zerhusen.security.service;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.zerhusen.security.SecurityUtils;
import org.zerhusen.security.model.User;
import org.zerhusen.security.repository.UserRepository;

@ApplicationScoped
public class UserService {

   private final UserRepository userRepository;

   @Inject
   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername()
         .flatMap(userRepository::findOneWithAuthoritiesByUsername);
   }
}
