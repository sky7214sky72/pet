package com.facilities.pet.web;

import com.facilities.pet.domain.user.User;
import com.facilities.pet.jwt.JwtFilter;
import com.facilities.pet.jwt.TokenProvider;
import com.facilities.pet.service.user.UserService;
import com.facilities.pet.web.dto.LoginDto;
import com.facilities.pet.web.dto.LogoutDto;
import com.facilities.pet.web.dto.TokenDto;
import com.facilities.pet.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<User> join(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.join(userDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> userLogin(@Valid @RequestBody LoginDto loginDto) {
        return userService.login(loginDto);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<LogoutDto> userLogout(@Valid @RequestBody LogoutDto logoutDto) {
        return ResponseEntity.ok(userService.logout(logoutDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        //isPresent,get() 대신에 orElse를 쓰자
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().orElse(null));
    }

    @GetMapping("/user/{phoneNumber}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getUserInfo(@PathVariable String phoneNumber) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(phoneNumber).orElse(null));
    }
}
