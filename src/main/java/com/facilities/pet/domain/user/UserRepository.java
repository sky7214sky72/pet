package com.facilities.pet.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * . UserRepository
 */
public interface UserRepository extends JpaRepository<User, Long> {

  @EntityGraph(attributePaths = "authorities")
  Optional<User> findOneWithAuthoritiesByEmail(String email);

  Optional<User> findByEmail(String email);
}
