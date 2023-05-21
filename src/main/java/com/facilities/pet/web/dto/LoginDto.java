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

  @NotNull
  @Schema(description = "핸드폰 번호")
  private String phoneNumber;

  @NotNull
  @Size(min = 3, max = 100)
  private String password;

  @Builder
  public LoginDto(String phoneNumber, String password) {
    this.phoneNumber = phoneNumber;
    this.password = password;
  }
}
