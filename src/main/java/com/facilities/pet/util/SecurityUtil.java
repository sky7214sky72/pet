package com.facilities.pet.util;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * . SecurityUtil
 */
public class SecurityUtil {

  private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

  private SecurityUtil() {

  }

  /**
   * . getCurrentEmail
   */
  public static Optional<String> getCurrentEmail() {
    //Security Context의 Authentication 객체를 이용해 username을 리턴
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      logger.debug("Security Context에 인증정보가 없습니다.");
      return Optional.empty();
    }
    String email = null;
    if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
      email = springSecurityUser.getUsername();
    } else if (authentication.getPrincipal() instanceof String) {
      email = (String) authentication.getPrincipal();
    }
    return Optional.ofNullable(email);
  }
}
