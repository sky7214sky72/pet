package com.facilities.pet.service.user;

import com.facilities.pet.domain.user.User;
import com.facilities.pet.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * . userDetailsService
 */
@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String phoneNumber) {
    return userRepository.findOneWithAuthoritiesByPhoneNumber(phoneNumber)
        .map(this::createUser)
        .orElseThrow(() -> new UsernameNotFoundException(phoneNumber + " -> 유저정보를 찾을 수 없습니다."));
  }

  private org.springframework.security.core.userdetails.User createUser(User user) {

    List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
        .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(),
        user.getPassword(),
        grantedAuthorities);
  }
}
