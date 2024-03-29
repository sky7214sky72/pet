package com.facilities.pet.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . TokenDto
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

  private String accessToken;
  private String refreshToken;
  private Long refreshTokenExpirationTime;
}
