package com.facilities.pet.config.security;

import com.facilities.pet.domain.user.UserRepository;
import com.facilities.pet.jwt.JwtAccessDeniedHandler;
import com.facilities.pet.jwt.JwtAuthenticationEntryPoint;
import com.facilities.pet.jwt.JwtSecurityConfig;
import com.facilities.pet.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * . SecurityConfig
 */
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final UserRepository userRepository;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * . SecurityFilterChain
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        //h2-console를 위한 설정
        .headers()
        .frameOptions()
        .sameOrigin()
        //세션 사용안함
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()//HttpServletRequest를 사용하는 요청에 대해 접근제한
        .requestMatchers("/swagger-ui/**").permitAll()
        .requestMatchers(new AntPathRequestMatcher("/api/login")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/api/naver/login")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/api/logout")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/api/join")).permitAll()
        .requestMatchers("/favicon.ico").permitAll()
        .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
        .requestMatchers("/v3/api-docs/**").permitAll()
        .requestMatchers("/api-docs/**").permitAll()
        .requestMatchers(new AntPathRequestMatcher("/swagger-resources/**")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html")).permitAll()
        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/index.html")).permitAll()
        .anyRequest().authenticated()//나머지 요청들은 인증을 받아야함
        //jwtsecurityconfig 적용
        .and()
        .apply(new JwtSecurityConfig(tokenProvider))
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .exceptionHandling()
        .accessDeniedHandler(jwtAccessDeniedHandler);
    return http.build();
  }
}

