package com.facilities.pet.config.security;

import com.facilities.pet.jwt.JwtAccessDeniedHandler;
import com.facilities.pet.jwt.JwtAuthenticationEntryPoint;
import com.facilities.pet.jwt.JwtSecurityConfig;
import com.facilities.pet.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//기본적인 web 보안 활성화
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//@PreAuthorize 어노테이션을 메소드단위로 추가하기 위해 적용
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
                .ignoring()
                .antMatchers(
                        "/h2-console/**"
                        ,"/favicon.ico"
                        ,"/api-docs/**"
                        ,"/swagger-ui/**"
                );
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                //h2-console를 위한 설정
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                //세션 사용안함
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()//HttpServletRequest를 사용하는 요청에 대해 접근제한
                .antMatchers("/api/login", "/api/logout", "/api/join").permitAll()
                .anyRequest().authenticated()//나머지 요청들은 인증을 받아야함
                //jwtsecurityconfig 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));
    }
}

