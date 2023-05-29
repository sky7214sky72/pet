package com.facilities.pet.web.dto;

import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.review.Review;
import com.facilities.pet.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . ReviewSaveRequestDto
 */
@Getter
@NoArgsConstructor
public class ReviewSaveRequestDto {

  private String title;
  private String content;

  /**
   * . ReviewSaveRequestDto
   */
  @Builder
  public ReviewSaveRequestDto(String title, String content) {
    this.title = title;
    this.content = content;
  }

  /**
   * . toEntity
   */
  public Review toEntity() {
    return Review.builder()
        .title(title)
        .content(content)
        .build();
  }
}
