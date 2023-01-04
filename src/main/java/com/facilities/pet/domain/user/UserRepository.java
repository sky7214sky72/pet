package com.facilities.pet.domain.user;

import com.facilities.pet.web.dto.UserDto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByPhoneNumber(String phoneNumber);
}
