package com.facilities.pet.web;

import com.facilities.pet.domain.user.User;
import com.facilities.pet.oauth.NaverLoginParams;
import com.facilities.pet.service.user.UserService;
import com.facilities.pet.web.dto.LoginDto;
import com.facilities.pet.web.dto.LogoutDto;
import com.facilities.pet.web.dto.TokenDto;
import com.facilities.pet.web.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * . UserController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  //회원가입
  @PostMapping("/join")
  public ResponseEntity<User> join(@Valid @RequestBody UserDto userDto) {
    return userService.join(userDto);
  }

  // 로그인
  @PostMapping("/login")
  public ResponseEntity<TokenDto> userLogin(@Valid @RequestBody LoginDto loginDto) {
    return userService.login(loginDto);
  }

  /**
   * . 네이버 로그인
   */
  @PostMapping("/naver/login")
  public ResponseEntity<TokenDto> naverLogin(@RequestBody NaverLoginParams params) {
    return userService.naverLogin(params);
  }

  /**
   * . 로그아웃
   */
  @PostMapping("/logout")
  public ResponseEntity<LogoutDto> userLogout(@Valid @RequestBody LogoutDto logoutDto) {
    return ResponseEntity.ok(userService.logout(logoutDto));
  }

  @GetMapping("/user")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<User> getMyUserInfo() {
    //isPresent,get() 대신에 orElse를 쓰자
    return ResponseEntity.ok(userService.getMyUserWithAuthorities().orElse(null));
  }

  @GetMapping("/user/email")
  @PreAuthorize("hasAnyRole('USER','ADMIN')")
  public ResponseEntity<User> getUserInfo(@RequestParam String email) {
    return userService.getUserWithAuthorities(email);
  }
}
