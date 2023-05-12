package com.facilities.pet.service.user;

import com.facilities.pet.domain.user.Authority;
import com.facilities.pet.domain.user.User;
import com.facilities.pet.domain.user.UserRepository;
import com.facilities.pet.jwt.JwtFilter;
import com.facilities.pet.jwt.TokenProvider;
import com.facilities.pet.util.SecurityUtil;
import com.facilities.pet.web.dto.LoginDto;
import com.facilities.pet.web.dto.LogoutDto;
import com.facilities.pet.web.dto.TokenDto;
import com.facilities.pet.web.dto.UserDto;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * . UserService
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final RedisTemplate<String, String> redisTemplate;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  /**
   * . join
   */
  @Transactional
  public User join(UserDto userDto) {
    logger.debug(String.valueOf(userDto));
    if (userRepository.findOneWithAuthoritiesByPhoneNumber(userDto.getPhoneNumber()).orElse(null)
        != null) {
      throw new IllegalArgumentException("이미 가입되어 있는 유저입니다.");
    }
    Authority authority = Authority.builder()
        .authorityName("ROLE_USER")
        .build();
    User user = User.builder()
        .username(userDto.getUsername())
        .password(passwordEncoder.encode(userDto.getPassword()))
        .phoneNumber(userDto.getPhoneNumber())
        .authorities(Collections.singleton(authority))
        .build();
    return userRepository.save(user);
  }

  /**
   * . login
   */
  @Transactional
  public ResponseEntity<TokenDto> login(LoginDto loginDto) {
    // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
    // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getPassword());
    // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
    // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
    Authentication authentication = authenticationManagerBuilder.getObject()
        .authenticate(authenticationToken);
    // 3. 인증 정보를 기반으로 JWT 토큰 생성
    TokenDto jwt = tokenProvider.createToken(authentication);
    // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
    redisTemplate.opsForValue()
        .set("RT:" + authentication.getName(), jwt.getRefreshToken(),
            jwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
  }

  /**
   * . logout
   */
  @Transactional
  public LogoutDto logout(LogoutDto logoutDto) {
    // 1. Access Token 검증
    if (!tokenProvider.validateToken(logoutDto.getAccessToken())) {
      throw new IllegalArgumentException("잘못된 요청입니다.");
    }

    // 2. Access Token 에서 phone number을 가져옵니다.
    Authentication authentication = tokenProvider.getAuthentication(logoutDto.getAccessToken());

    // 3. Redis 에서 해당 phone number 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
    if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
      // Refresh Token 삭제
      redisTemplate.delete("RT:" + authentication.getName());
    }

    // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
    Long expiration = tokenProvider.getExpiration(logoutDto.getAccessToken());
    redisTemplate.opsForValue()
        .set(logoutDto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
    return logoutDto;
  }

  /**
   * . getUserWithAuthorities
   */
  @Transactional(readOnly = true)
  public Optional<User> getUserWithAuthorities(String phoneNumber) {
    //유저, 권한정보를 가져오는 메소드(username)을 기준으로
    return userRepository.findOneWithAuthoritiesByPhoneNumber(phoneNumber);
  }

  /**
   * . getMyUserWithAuthorities
   */
  @Transactional(readOnly = true)
  public Optional<User> getMyUserWithAuthorities() {
    //Security Context에 저장된 username의 정보만 가져온다
    return SecurityUtil.getCurrentPhoneNumber()
        .flatMap(userRepository::findOneWithAuthoritiesByPhoneNumber);
  }

}
