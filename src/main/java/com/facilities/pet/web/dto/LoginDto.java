package com.facilities.pet.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . LoginDto
 */
@Getter
@NoArgsConstructor
public class LoginDto {

  @Schema(description = "이메일")
  private String email;
  @Schema(description = "핸드폰 번호")
  private String phoneNumber;
  @Schema(description = "비밀 번호")
  private String password;

  @Builder
  public LoginDto(String email, String phoneNumber, String password) {
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }
}
