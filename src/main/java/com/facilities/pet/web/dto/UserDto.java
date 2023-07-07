package com.facilities.pet.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . UserDto
 */
@Getter
@NoArgsConstructor
public class UserDto {

  @Schema(description = "이메일 주소")
  private String email;

  @Schema(description = "비밀번호")
  private String password;

  @Schema(description = "핸드폰 번호")
  private String phoneNumber;

  /**
   * . UserDto
   */
  @Builder
  public UserDto(String email, String password, String phoneNumber) {
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
  }
}
