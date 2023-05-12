package com.facilities.pet.domain.review;

import com.facilities.pet.domain.BaseTimeEntity;
import com.facilities.pet.domain.pet.PetCompany;
import com.facilities.pet.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * . Review
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review extends BaseTimeEntity {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 50, nullable = false)
  private String title;

  @Lob
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "COMPANY_ID")
  private PetCompany petCompany;

  /**
   * . Review
   */
  @Builder
  public Review(String title, String content, User user, PetCompany petCompany) {
    this.title = title;
    this.content = content;
    this.user = user;
    this.petCompany = petCompany;
  }

  public void update(String title, String content) {
    this.title = title;
    this.content = content;
  }
}
