package com.facilities.pet.jwt;

import com.facilities.pet.web.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long accessTokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
            @Value("${jwt.access-token-validity-in-seconds}") long refreshTokenValidityInSeconds) {
        this.secret = secret;
        this.accessTokenValidityInMilliseconds = accessTokenValidityInSeconds * 1000L;
        this.refreshTokenValidityInMilliseconds = refreshTokenValidityInSeconds * 1000L;
    }
    //의존성 주입 후 주입 받은 시크릿 값을 디코드해서 키에 저장
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    //권한정보 이용해서 토큰 생성하는 메소드
    public TokenDto createToken(Authentication authentication) {
        //권한들
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        //만료시간
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.accessTokenValidityInMilliseconds);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
        //refresh 토큰
        Date refreshValidity = new Date(now + this.refreshTokenValidityInMilliseconds);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshValidity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshValidity.getTime())
                .build();
    }

    //토큰 정보를 리턴해서 Authentication 객체를 리턴받음
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    //토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Long getExpiration(String accessToken) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
