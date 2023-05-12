package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . ReviewUpdateRequestDto
 */
@Getter
@NoArgsConstructor
public class ReviewUpdateRequestDto {

  private String title;
  private String content;

  /**
   * . ReviewUpdateRequestDto
   */
  @Builder
  public ReviewUpdateRequestDto(String title, String content, User user, PetCompany petCompany) {
    this.title = title;
    this.content = content;
  }
}
