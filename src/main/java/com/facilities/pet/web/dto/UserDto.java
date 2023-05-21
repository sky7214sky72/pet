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

  @NotNull
  @Size(min = 3, max = 50)
  private String username;

  @NotNull
  @Size(min = 3, max = 100)
  private String password;

  @NotNull
  @Schema(description = "핸드폰 번호")
  private String phoneNumber;

  /**
   * . UserDto
   */
  @Builder
  public UserDto(String username, String password, String phoneNumber) {
    this.username = username;
    this.password = password;
    this.phoneNumber = phoneNumber;
  }
}
