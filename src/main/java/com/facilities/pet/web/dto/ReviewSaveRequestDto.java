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
  private User user;
  private PetCompany petCompany;

  /**
   * . ReviewSaveRequestDto
   */
  @Builder
  public ReviewSaveRequestDto(String title, String content, User user, PetCompany petCompany) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.petCompany = petCompany;
  }

  /**
   * . setUser
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * . setPetCompany
   */
  public void setPetCompany(PetCompany petCompany) {
    this.petCompany = petCompany;
  }

  /**
   * . toEntity
   */
  public Review toEntity() {
    return Review.builder()
        .title(title)
        .content(content)
        .user(user)
        .petCompany(petCompany)
        .build();
  }
}
