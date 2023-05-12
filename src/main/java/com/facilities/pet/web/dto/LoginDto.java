package com.facilities.pet.web.dto;

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
  @Size(min = 11, max = 11)
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
