package com.facilities.pet.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . LogoutDto
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogoutDto {

  private String accessToken;
  private String refreshToken;
}
