package com.facilities.pet.service.user;

import com.facilities.pet.domain.enums.OauthProvider;
import com.facilities.pet.domain.user.Authority;
import com.facilities.pet.domain.user.User;
import com.facilities.pet.domain.user.UserRepository;
import com.facilities.pet.jwt.JwtFilter;
import com.facilities.pet.jwt.TokenProvider;
import com.facilities.pet.oauth.NaverLoginParams;
import com.facilities.pet.oauth.OauthInfoResponse;
import com.facilities.pet.oauth.RequestOauthInfoService;
import com.facilities.pet.util.SecurityUtil;
import com.facilities.pet.web.dto.LoginDto;
import com.facilities.pet.web.dto.TokenDto;
import com.facilities.pet.web.dto.UserDto;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
  private final RequestOauthInfoService requestOauthInfoService;
  //  private final RedisTemplate<String, String> redisTemplate;
  private final Logger logger = LoggerFactory.getLogger(UserService.class);

  /**
   * . join
   */
  @Transactional
  public ResponseEntity<User> join(UserDto userDto) {
    if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null)
        != null) {
      throw new IllegalArgumentException("이미 가입되어 있는 유저입니다.");
    }
    Authority authority = Authority.builder()
        .authorityName("ROLE_USER")
        .build();
    User user = User.builder()
        .email(userDto.getEmail())
        .password(passwordEncoder.encode(userDto.getPassword()))
        .phoneNumber(userDto.getPhoneNumber())
        .authorities(Collections.singleton(authority))
        .build();
    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
  }

  /**
   * . login
   */
  @Transactional
  public ResponseEntity<TokenDto> login(LoginDto loginDto) {
    return getLogin(loginDto.getEmail(), loginDto.getPassword());
  }

  /**
   * . naver login
   */
  @Transactional
  public ResponseEntity<TokenDto> naverLogin(NaverLoginParams params) {
    OauthInfoResponse oauthInfoResponse = requestOauthInfoService.request(params);
    if (userRepository.findByEmail(oauthInfoResponse.getEmail()).isEmpty()) {
      Authority authority = Authority.builder()
          .authorityName("ROLE_USER")
          .build();
      User user = User.builder()
          .email(oauthInfoResponse.getEmail())
          .password(oauthInfoResponse.getEmail())
          .authorities(Collections.singleton(authority))
          .oauthProvider(OauthProvider.NAVER.name())
          .build();
      userRepository.save(user);
    }
    return getLogin(oauthInfoResponse.getEmail(), oauthInfoResponse.getEmail());
  }

  /**
   * . logout
   */
//  @Transactional
//  public LogoutDto logout(LogoutDto logoutDto) {
//    // 1. Access Token 검증
//    if (!tokenProvider.validateToken(logoutDto.getAccessToken())) {
//      throw new IllegalArgumentException("잘못된 요청입니다.");
//    }
//
//    // 2. Access Token 에서 phone number을 가져옵니다.
//    Authentication authentication = tokenProvider.getAuthentication(logoutDto.getAccessToken());
//
//    // 3. Redis 에서 해당 phone number 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
//    if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
//      // Refresh Token 삭제
//      redisTemplate.delete("RT:" + authentication.getName());
//    }
//
//    // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
//    Long expiration = tokenProvider.getExpiration(logoutDto.getAccessToken());
//    redisTemplate.opsForValue()
//        .set(logoutDto.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);
//    return logoutDto;
//  }

  /**
   * . getUserWithAuthorities
   */
  @Transactional(readOnly = true)
  public ResponseEntity<User> getUserWithAuthorities(String email) {
    //유저, 권한정보를 가져오는 메소드(username)을 기준으로
    return new ResponseEntity<>(
        userRepository.findOneWithAuthoritiesByEmail(email)
            .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다")),
        HttpStatus.OK);
  }

  /**
   * . getMyUserWithAuthorities
   */
  @Transactional(readOnly = true)
  public Optional<User> getMyUserWithAuthorities() {
    //Security Context에 저장된 username의 정보만 가져온다
    return SecurityUtil.getCurrentEmail()
        .flatMap(userRepository::findOneWithAuthoritiesByEmail);
  }

  /**
   * . 인증 및 토큰 발급 공통 함수
   */
   private ResponseEntity<TokenDto> getLogin(String userId, String password) {
     // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
     // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
     UsernamePasswordAuthenticationToken authenticationToken =
         new UsernamePasswordAuthenticationToken(userId, password);
     // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
     // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
     Authentication authentication = authenticationManagerBuilder.getObject()
         .authenticate(authenticationToken);
     // 3. 인증 정보를 기반으로 JWT 토큰 생성
     TokenDto jwt = tokenProvider.createToken(authentication);
     // 4. RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
//    redisTemplate.opsForValue()
//        .set("RT:" + authentication.getName(), jwt.getRefreshToken(),
//            jwt.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);
     HttpHeaders httpHeaders = new HttpHeaders();
     httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
     return new ResponseEntity<>(jwt, httpHeaders, HttpStatus.OK);
   }
}
